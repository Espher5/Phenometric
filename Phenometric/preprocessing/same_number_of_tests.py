from statistics import median
from os import listdir, path
from random import choice
from shutil import copyfile

def same_number_of_tests():
    loc_flaky = []

    # calculating the lines of code for each flaky test sample
    for filename in listdir('C:\\Users\\Roberto\\Desktop\\test_cases_samples_flaky'):
        with open(path.join('C:\\Users\\Roberto\\Desktop\\test_cases_samples_flaky', filename), 'r', encoding = 'utf8') as f:
            lines = f.readlines()

            count = 0

            for line in lines:
                for word in line.split():
                    if '//' in word or '/*' in word or '*/' in word or '*' in word:
                        break
                    else:
                        count += 1
                        break

            loc_flaky.append(count)

    # calculating the median size of lines of code of the flaky test samples
    median_flaky = median(loc_flaky)

    loc_nonflaky = {}

    # calculating the lines of code for each non-flaky test sample
    for filename in listdir('C:\\Users\\Roberto\\Desktop\\test_cases_samples_nonflaky'):
        with open(path.join('C:\\Users\\Roberto\\Desktop\\test_cases_samples_nonflaky', filename), 'r', encoding='utf8') as f:
            lines = f.readlines()

            count = 0

            for line in lines:
                for word in line.split():
                    if '//' in word or '/*' in word or '*/' in word or '*' in word:
                        break
                    else:
                        count += 1
                        break

            loc_nonflaky[filename] = count

    file_counter = 0 # variable to track the number of files chosen as non-flaky test sample
    flag = 0 # to select alternately a test of size above the median and a test size below the median

    while file_counter < 1402: # repeating the process until we get the same number of files as flaky test samples, i.e. 1402

        name, lines_of_code = choice(list(loc_nonflaky.items())) # random non-flaky test sample

        if lines_of_code >= median_flaky and flag == 0:
            try:
                with open(path.join('C:\\Users\\Roberto\\Desktop\\chosen_test_cases_nonflaky', name), 'r', encoding='utf8') as f:
                    continue
            except FileNotFoundError:
                src_cases = 'C:\\Users\\Roberto\\Desktop\\test_cases_samples_nonflaky\\' + name
                dst_cases = 'C:\\Users\\Roberto\\Desktop\\chosen_test_cases_nonflaky\\' + name
                src_tokens = 'C:\\Users\\Roberto\\Desktop\\test_tokens_samples_nonflaky\\' + name
                dst_tokens = 'C:\\Users\\Roberto\\Desktop\\chosen_test_tokens_nonflaky\\' + name
                copyfile(src_cases, dst_cases)
                copyfile(src_tokens, dst_tokens)
                file_counter += 1
                flag = 1
        elif lines_of_code <= median_flaky and flag == 1:
            try:
                with open(path.join('C:\\Users\\Roberto\\Desktop\\chosen_test_cases_nonflaky', name), 'r', encoding='utf8') as f:
                    continue
            except FileNotFoundError:
                src_cases = 'C:\\Users\\Roberto\\Desktop\\test_cases_samples_nonflaky\\' + name
                dst_cases = 'C:\\Users\\Roberto\\Desktop\\chosen_test_cases_nonflaky\\' + name
                src_tokens = 'C:\\Users\\Roberto\\Desktop\\test_tokens_samples_nonflaky\\' + name
                dst_tokens = 'C:\\Users\\Roberto\\Desktop\\chosen_test_tokens_nonflaky\\' + name
                copyfile(src_cases, dst_cases)
                copyfile(src_tokens, dst_tokens)
                file_counter += 1
                flag = 0

if __name__ == '__main__':
    same_number_of_tests()