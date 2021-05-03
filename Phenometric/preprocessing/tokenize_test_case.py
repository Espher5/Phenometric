import os
import re
import subprocess


def tokenize(path):
    process = subprocess.Popen(['java', '-jar', os.path.join(os.getcwd(), 'utils\\vis_ids\\jar\\vis_ids.jar'), path],
                               stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
    body, stderr = process.communicate()
    tokens = body.decode('windows-1252').split('\r\n')
    all_tokens = list()
    for token in tokens:
        all_tokens.append(token.lower())
        split_tokens = re.findall('[A-Z][^A-Z]*', token)
        for split_token in split_tokens:
            if len(split_token) >= 2:
                all_tokens.append(split_token.lower())
        first_lowercase = re.findall('[a-z]+[A-Z]?', token)
        if first_lowercase and len(first_lowercase[0]) > 2 and first_lowercase[0] not in all_tokens:
            all_tokens.append(first_lowercase[0][:-1])
    try:
        all_tokens.remove('')
        all_tokens.remove('"')
    except ValueError:
        pass
    return all_tokens
