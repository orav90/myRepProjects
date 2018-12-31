import ListClass as ls

# Class Queue
class Queue(object):
    def __init__(self):
        self._queue=ls.List()

    def enqueue(self,value):
        self._queue.addToTail(value)

    def dequeue(self):
        if(self.isQueueEmpty()):
            print "The queue is empty"
            return None
        head=self._queue.getHead()
        self._queue.remove(self._queue.getHeadData())
        return head

    #Get the first node pointer from queue
    def front(self):
        if(self.isQueueEmpty()):
            print "The queue is empty"
            return None
        return self._queue.getHeadData()

    def isQueueEmpty(self):
        return self._queue.isEmpty()

    def printQueue(self):
        if(self.isQueueEmpty()):
            print "The queue is empty"
            return
        self._queue.printList()

    def queueSize(self):
        return self._queue.listSize()