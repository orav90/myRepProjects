import mongoose from 'mongoose';


const pullRequestSchema = mongoose.Schema({
    _id : Number,
    url: String,
    username: String,
    title: String,
    body: String,
    createdAt: String
})


const PullRequest = mongoose.model('PullRequest',pullRequestSchema)

export default PullRequest;