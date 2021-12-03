from PIL import Image
import os, glob, numpy as np
from sklearn.model_selection import train_test_split

caltech_dir = "C:/Users/82108/PycharmProjects/ai2020"
categories = [ "산불3천","산3천개","산사태3천","안개3천"]
nb_classes = len(categories)

image_w = 227
image_h = 227

pixels = image_h * image_w * 3

X = []
y = []

for idx, mountain in enumerate(categories):

    label = [0 for i in range(nb_classes)]
    label[idx] = 1

    image_dir = caltech_dir + "/" + mountain
    files = glob.glob(image_dir + "/*.jpg")
    print(mountain, " 파일 길이 : ", len(files))
    for i, f in enumerate(files):
        img = Image.open(f)
        img = img.convert("RGB")
        img = img.resize((image_w, image_h))
        data = np.asarray(img)

        X.append(data)
        y.append(label)


X = np.array(X)
y = np.array(y)

X_train, X_test, y_train, y_test = train_test_split(X, y)
xy = (X_train, X_test, y_train, y_test)
np.save('C:/Users/82108/PycharmProjects/ai2020/프로젝트.npy', xy)

print("ok", len(y))

import os,  numpy as np
from keras.models import Sequential
from keras.layers import Conv2D, MaxPooling2D, Dense, Flatten, Dropout
from keras.callbacks import EarlyStopping, ModelCheckpoint
import matplotlib.pyplot as plt
import keras.backend.tensorflow_backend as M

X_train, X_test, y_train, y_test = np.load('C:/Users/82108/PycharmProjects/ai2020/프로젝트.npy')
print(X_train.shape)
print(X_train.shape[0])

categories = [ "산불3천","산3천개","산사태3천","안개3천"]
nb_classes = len(categories)

X_train = X_train.astype(float) / 255
X_test = X_test.astype(float) / 255

with M.tf_ops.device('/device:GPU:0'):
    model = Sequential()
    model.add(Conv2D(filters=96, input_shape=X_train.shape[1:], kernel_size=(11, 11), strides=(4, 4), padding='valid',
                    activation='relu'))
    model.add(MaxPooling2D(pool_size=(3, 3), strides=(2, 2), padding='valid'))

    model.add(Conv2D(filters=256, kernel_size=(5,5), strides=(1, 1), padding='same', activation='relu'))
    model.add(MaxPooling2D(pool_size=(3, 3), strides=(2, 2), padding='valid'))

    model.add(Conv2D(filters=384, kernel_size=(3, 3), strides=(1, 1), padding='same', activation='relu'))

    model.add(Conv2D(filters=384, kernel_size=(3, 3), strides=(1, 1), padding='same', activation='relu'))

    model.add(Conv2D(filters=256, kernel_size=(3, 3), strides=(1, 1), padding='same', activation='relu'))

    model.add(MaxPooling2D(pool_size=(3, 3), strides=(2, 2), padding='valid'))
    model.add(Flatten())

    model.add(Dense(4096, input_shape=X_train.shape[1:], activation='relu'))
    model.add(Dropout(0.5))

    model.add(Dense(4096, activation='relu'))
    model.add(Dropout(0.5))

    model.add(Dense(1000, activation='relu'))
    model.add(Dropout(0.5))

    model.add(Dense(nb_classes, activation='softmax'))
    model.compile(loss='categorical_crossentropy', optimizer='adam', metrics=['accuracy'])
    model_dir = './model'

    if not os.path.exists(model_dir):
        os.mkdir(model_dir)

    model_path = model_dir + '/multi_img_classification.model'
    checkpoint = ModelCheckpoint(filepath=model_path, monitor='val_loss', verbose=1, save_best_only=True)
    early_stopping = EarlyStopping(monitor='val_loss', patience=4)

    model.summary()

history = model.fit(X_train, y_train, batch_size=256, epochs=50, validation_data=(X_test, y_test), callbacks=[checkpoint, early_stopping])

print("정확도 : %.4f" % (model.evaluate(X_test, y_test)[1]))

y_vloss = history.history['val_loss']
y_loss = history.history['loss']

x_len = np.arange(len(y_loss))

plt.plot(x_len, y_vloss, marker='.', c='red', label='val_set_loss')
plt.plot(x_len, y_loss, marker='.', c='blue', label='train_set_oss')
plt.legend()
plt.xlabel('epochs')
plt.ylabel('loss')
plt.grid()
plt.show()


from PIL import Image
import glob, numpy as np
from keras.models import load_model

caltech_dir = "C:/Users/82108/PycharmProjects/ai2020/테스트"
image_w = 227
image_h = 227

pixels = image_h * image_w * 3

X = []
filenames = []
files = glob.glob(caltech_dir+"/*.*")
for i, f in enumerate(files):
    img = Image.open(f)
    img = img.convert("RGB")
    img = img.resize((image_w, image_h))
    data = np.asarray(img)
    filenames.append(f)
    X.append(data)

X = np.array(X)
model = load_model('./model/multi_img_classification.model')

prediction = model.predict(X)
np.set_printoptions(formatter={'float': lambda x: "{0:0.3f}".format(x)})
cnt = 0

for i in prediction:
    answer = i.argmax()
    print(i)
    print(answer)
    answer_str = ''
    if answer == 0: answer_str = "산불"
    elif answer == 1:
        answer_str = "산"
    elif  answer == 2:
        answer_str = "산사태"
    else: answer_str = "안개"
    if i[0] >= 0.8 : print("해당 "+filenames[cnt].split("\\")[1]+"이미지는 "+answer_str+"로 추정됩니다.")
    if i[1] >= 0.8: print("해당 "+filenames[cnt].split("\\")[1]+"이미지는 "+answer_str+"로 추정됩니다.")
    if i[2] >= 0.8: print("해당 " + filenames[cnt].split("\\")[1] + "이미지는 " + answer_str + "으로 추정됩니다.")
    if i[3] >= 0.8: print("해당 " + filenames[cnt].split("\\")[1] + "이미지는 " + answer_str + "로 추정됩니다.")

    cnt += 1