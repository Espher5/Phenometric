import csv
import os
from loc import loc
from keyword_parser import parse_java_keywords, JAVA_KEYWORDS
from tokenize_test_case import tokenize


basedir = 'dataset'
flaky_test_dir = basedir + '\\samples_flaky\\test_cases'
non_flaky_test_dir = basedir + '\\samples_non_flaky\\test_cases'
flaky_token_dir = basedir + '\\samples_flaky\\test_tokens'
non_flaky_token_dir = basedir + '\\samples_non_flaky\\test_tokens'


def main():
    csv_file = open(basedir + '\\dataset.csv', mode='w', newline='')
    csv_writer = csv.writer(csv_file)
    csv_writer.writerow(
        ['id', 'tokens', 'loc'] + [keyword + '_keywords' for keyword in JAVA_KEYWORDS] + ['@@class@@']
    )

    test_cases = dict()
    for t in os.listdir(flaky_token_dir):
        test_cases[t] = 'flaky'
    for t in os.listdir(non_flaky_token_dir):
        test_cases[t] = 'non_flaky'

    test_id = 1
    for test_case in test_cases.keys():
        print('{} - Processing file: {}'.format(test_id, test_case))
        file_path = flaky_test_dir if test_cases[test_case] == 'flaky' else non_flaky_test_dir

        tokens = tokenize(os.path.join(file_path, test_case))
        parsed_tokens_str = ''
        for i in range(len(tokens)):
            if i == len(tokens) - 1:
                parsed_tokens_str += tokens[i]
            else:
                parsed_tokens_str += tokens[i] + '  '
        test_loc = loc(os.path.join(file_path, test_case))
        java_keywords, keyword_count = parse_java_keywords(os.path.join(file_path, test_case))
        csv_writer.writerow(
            [test_id] +
            [parsed_tokens_str] +
            [test_loc] +
            [keyword for keyword in java_keywords.values()] +
            [keyword_count] +
            [test_cases[test_case]]
        )
        test_id += 1
    csv_file.close()


if __name__ == '__main__':
    main()
