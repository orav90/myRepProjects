import express from 'express'
import PullRequest from '../pullRequestModel.js'
const router = express.Router()
import captureWebsite from 'capture-website';


router.post('/event_handler', async (req,res) =>{
    let event = req.headers['x-github-event']
    switch(event){
        case "pull_request":{
            const pullRequest = new PullRequest({
                _id: req.body.pull_request.id,
                username: req.body.sender.login,
                url: req.body.pull_request.url,
                title: req.body.pull_request.title,
                body: req.body.pull_request.body,
                createdAt: req.body.pull_request.created_at
            })
            try {
                await pullRequest.save()
                res.status(201)
            } catch (err) {
                res.status(400)
            }
            
        }
    }
})

router.get('/pulls', async(req,res) =>{

    try {
        const pullRequests = await PullRequest.find()
        res.status(201).json(pullRequests)
      } catch (err) {
        res.status(400).json({ message: err.message })
       
      }


})

router.get('/image', async(req,res) =>{
    
    try{
        await captureWebsite.file('https://github.com/orav90/demoproject/pulls', 'public/screenshot.png',{overwrite:true,});
        res.status(200)
      }catch(err ){
        res.status(400).json({ message: err.message })
      }
})

export default router