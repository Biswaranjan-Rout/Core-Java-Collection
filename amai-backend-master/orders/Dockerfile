#User node js official image as a parent image
FROM node

ADD package.json /tmp/package.json

RUN cd /tmp && npm install

RUN mkdir -p /src && cp -a /tmp/node_modules /src/

COPY . /src

WORKDIR /src

RUN npm install

EXPOSE 3000

CMD npm start
