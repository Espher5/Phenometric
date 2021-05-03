from nltk import word_tokenize, download, PorterStemmer
from nltk.corpus import stopwords
from wordsegment import load, segment

download('punkt')
download('wordnet')
download('stopwords')
stop_words = set(stopwords.words('english'))
other_stop_words = [",", "?", "-", "_", ";", "\"", "—", "\\n", "==", "0", "1", "2", "3", "4", "-c", "*", "=", "/",
                    "@", "$", ";", ":", "(", ")", "<", ">", "{", "}", ".", "''", "'", "``", "get", "set", "test"]
stop_words = stop_words.union(set(other_stop_words))
load()


def read_words(text):
    tokens = word_tokenize(text)
    words = list()
    for token in tokens:
        if len(token) > 100:
            index = 5
            while index < len(token):
                words += segment(token[index - 5: index + 100])
                index += 100
        else:
            for word in segment(token):
                words.append(word)

    stemmer = PorterStemmer()
    stems = set()
    for w in words:
        stems.add(stemmer.stem(w))
    filtered_stems = [w for w in stems if w not in stop_words]
    return set(filtered_stems)


def extract_features(path):
    with open(path, 'r', encoding='utf-8') as file:
        data = file.read().replace('\n', '')
        test_words = read_words(data)
    return list(test_words)
