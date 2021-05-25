import pandas as pd
import numpy as np

dataset = pd.read_csv(r"C:\Users\anton\OneDrive\Desktop\dataset1.csv")

dataset.iloc[0:2803,58]

dataset.head()

X = dataset.iloc[0:2803,0:1609].values
y = dataset.iloc[0:2803,58].values

from sklearn.model_selection import train_test_split
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=0)

from sklearn.ensemble import RandomForestClassifier
clf=RandomForestClassifier(n_estimators=1000)
#Train the model using the training sets y_pred=clf.predict(X_test)
clf.fit(X_train,y_train)
y_pred=clf.predict(X_test)


from sklearn.metrics import accuracy_score, roc_auc_score, precision_score, f1_score, recall_score, matthews_corrcoef
# accuracy: (tp + tn) / (p + n)
acc=(accuracy_score(y_test, y_pred))
print("Accuracy score: %f" % acc)

auc=(roc_auc_score(y_test, y_pred))
print("AUC: %f" % auc)

mcc=(matthews_corrcoef(y_test, y_pred))
print("MCC: %f" % mcc)

# precision tp / (tp + fp)
pre=(precision_score(y_test, y_pred))
print("Precision score: %f" % pre)

# recall: tp / (tp + fn)
rec=(recall_score(y_test, y_pred))
print("Recall score: %f" % rec)

# f1: 2 tp / (2 tp + fp + fn)
f1=(f1_score(y_test, y_pred))
print("F1 score: %f" % f1)
