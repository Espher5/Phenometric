import pandas as pd
import numpy as np

dataset = pd.read_csv(r"C:\Users\anton\OneDrive\Desktop\dataset1.csv")

dataset.iloc[0:2803,58]

dataset.head()

X = dataset.iloc[0:2803,0:1609].values
y = dataset.iloc[0:2803,58].values

from sklearn.model_selection import train_test_split
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, stratify=y, random_state=0)



from sklearn.svm import SVC
from sklearn.metrics import confusion_matrix, classification_report
from sklearn.preprocessing import StandardScaler

# Important to scale
sc = StandardScaler()
X_train = sc.fit_transform(X_train)
X_test = sc.transform(X_test)

clf = SVC()
clf.fit(X_train, y_train)
pred = clf.predict(X_test)

print(classification_report(y_test, pred))
print(confusion_matrix(y_test, pred))
