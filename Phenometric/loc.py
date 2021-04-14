def loc(path):
    with open(path, 'r') as file:
        data = file.read().split('\n')
        return len(data)
