from nltk import word_tokenize, download, WordNetLemmatizer
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
    words = [word for token in tokens for word in segment(token)]

    stemmer = WordNetLemmatizer()
    stems = set()
    for w in words:
        stems.add(stemmer.lemmatize(w))
    filtered_stems = [w for w in stems if w not in stop_words]
    return set(filtered_stems)


def extract_features(path):
    with open(path, 'r') as file:
        data = file.read().replace('\n', '')
        test_words = read_words(data)
    return list(test_words)
