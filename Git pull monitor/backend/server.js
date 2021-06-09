import express from 'express'
import dotenv from 'dotenv'
import mongoose from 'mongoose'
import router from "./routes/pullRequests.js"
import cors from 'cors'

dotenv.config()
const app = express()
app.use(cors())

app.use(express.static('public'));

mongoose.connect(process.env.MONGO_DB, { useNewUrlParser: true, useUnifiedTopology: true })
const db = mongoose.connection
db.on('error', (error) => console.error(error))
db.once('open', () => console.log('Connected to Database'))


app.use(express.json())

app.use("/",router)

app.listen(5000, () => console.log("Server started"))