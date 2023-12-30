FROM node:20-alpine

WORKDIR /app

COPY . .

# Fix npm connect error
RUN npm config set registry https://registry.npmmirror.com/

# Set env for compose
ENV API_SERVER_HOST=noisy-backend

# Install dependencies & build
RUN npm install
RUN npm run build

EXPOSE 3000

CMD [ "node", ".output/server/index.mjs" ]