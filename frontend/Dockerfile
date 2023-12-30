FROM node:20-alpine

WORKDIR /app

COPY . .

# Set env for compose
ENV API_SERVER_HOST=noisy-backend

# Install dependencies & build
RUN npm install
RUN npm run build

EXPOSE 3000

CMD [ "node", ".output/server/index.mjs" ]