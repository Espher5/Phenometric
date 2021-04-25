import re

JAVA_KEYWORDS = ['abstract', 'assert', 'boolean', 'break',
                 'byte', 'case', 'catch', 'char', 'class', 'continue',
                 'default', 'do', 'double', 'else', 'enum', 'exports',
                 'extends', 'final', 'finally', 'float', 'for', 'if',
                 'implements', 'import', 'instanceof', 'int', 'interface',
                 'long', 'modules', 'native', 'new', 'package', 'private',
                 'protected', 'public', 'requires', 'return', 'short', 'static',
                 'strictfp', 'super', 'switch', 'synchronized', 'this', 'throw',
                 'throws', 'transient', 'try', 'void', 'volatile', 'while', 'true',
                 'null', 'false', 'const', 'goto']


def parse_java_keywords(path):
    keywords = dict()
    for k in JAVA_KEYWORDS:
        keywords[k] = 0
    keyword_count = 0
    with open(path, 'r', encoding='utf8') as f:
        lines = f.readlines()
        for line in lines:
            for word in line.split():
                # removing some characters from the word
                split_words = re.split('\'|&|=|!|\?|,|;|:|\(|\)|[|]|{​​|}​​', word)
                # checking if the word is a comment or a string
                if '//' in word or '/*' in word or '*/' in word or '"' in word:
                    break
                else:
                    for split_word in split_words:
                        for key in JAVA_KEYWORDS:
                            if split_word == key:
                                keyword_count += 1
                                try:
                                    keywords[key] += 1
                                except KeyError:
                                    keywords[key] = 1
                                break
    return keywords, keyword_count
