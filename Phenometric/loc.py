def loc(path):
    with open(path, 'r') as file:
        count = 0
        lines = file.readlines()
        for line in lines:
            for word in line.split():
                if '//' in word or '/*' in word or '*/' in word or '*' in word:
                    break
                else:
                    count += 1
                    break
        return count
