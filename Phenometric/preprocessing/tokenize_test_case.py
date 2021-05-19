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
        matches = re.finditer('.+?(?:(?<=[a-z])(?=[A-Z])|(?<=[A-Z])(?=[A-Z][a-z])|$)', token)
        for m in matches:
            all_tokens.append(m.group(0).lower())
    if '' in all_tokens:
        all_tokens.remove('')
    return list(all_tokens)
