FROM node

WORKDIR /usr/src/app

COPY . /usr/src/app

RUN npm install --force -g @angular/cli 

RUN npm install

CMD ["ng", "serve", "--host", "0.0.0.0"]
