import csv
import os
from collections import OrderedDict

base_dir = 'C:\\Users\\ikime\\Desktop'
prob_all_outfile = os.path.join(base_dir, 'reruns', 'prob_all.csv')
RUNS = 100


def consolidate_results(project):
    project_dir = os.path.join(base_dir, 'reruns', project)
    non_flaky_outfile = os.path.join(project_dir, 'nonflaky_list.txt')
    oth_outfile = os.path.join(project_dir, 'results_distribution.txt')
    prob_outfile = os.path.join(project_dir, 'prob.csv')

    results = OrderedDict()

    print('Consolidating result for project {}'.format(project))
    for i in range(RUNS):
        log_file = os.path.join(project_dir, 'run{}.log'.format(i + 1))
        log = csv.reader(open(log_file, 'r'), delimiter=',')

        for row in log:
            if len(row) == 3:
                classname, name, status = row
            elif len(row) == 4:
                classname, name1, name2, status = row
            tc = classname.strip() + '---' + name.strip()
            status = status.strip()

            if status not in ['pass', 'fail', 'error', 'skip']:
                print('Status not in ["pass", "fail", "error", "skip"]')
            if tc not in results:
                results[tc] = {}

            try:
                results[tc][status] += 1
            except KeyError:
                results[tc][status] = 1

    with open(non_flaky_outfile, 'w') as non_flaky_out, open(oth_outfile, 'w') as oth_out, open(prob_outfile, 'w') as prob_out, open(prob_all_outfile, 'a') as prob_all_out:
        oth_out.write('TC PASS FAIL ERROR SKIP\n')
        prob_out.write('TC, PASS, FAIL, ERROR, SKIP\n')
        for tc in results:
            classname, name = tc.split('---')
            c_pass = results[tc]['pass'] if 'pass' in results[tc] else 0
            c_fail = results[tc]['fail'] if 'fail' in results[tc] else 0
            c_error = results[tc]['error'] if 'error' in results[tc] else 0
            c_skip = results[tc]['skip'] if 'skip' in results[tc] else 0
            c_total = c_pass + c_fail + c_error + c_skip

            if c_pass / c_total == 1 or c_fail / c_total == 1 or c_error / c_total == 1 or c_skip / c_total == 1:
                non_flaky_out.write('{}.{}\n'.format(classname, name))
            else:
                res = '{} {} {} {}'.format(c_pass, c_fail, c_error, c_skip)
                oth_out.write('{}.{} {}'.format(classname, name, res))
                res_prob = '{},{},{},{}'.format(c_pass / c_total, c_fail / c_total, c_error / c_total, c_skip / c_total)
                prob_out.write('{}.{},{}\n'.format(classname, name, res_prob))
                prob_all_out.write('{},{}.{},{}\n'.format(project, classname, name, res_prob))


def main():
    all_projects = sorted([
        p for p in os.listdir(os.path.join(base_dir, 'reruns'))
        if os.path.isdir(os.path.join(base_dir, 'reruns', p))
        and p.endswith('-100')
    ])
    skip_list = ['hadoop-100', 'alluxio-100']

    if os.path.exists(prob_all_outfile):
        os.remove(prob_all_outfile)
        with open(prob_all_outfile, 'w') as prob_all_out:
            prob_all_out.write('PROJECT, TC, PASS, FAIL, ERROR, SKIP\n')

    for project in all_projects:
        if project not in skip_list:
            consolidate_results(project)


if __name__ == '__main__':
    main()
