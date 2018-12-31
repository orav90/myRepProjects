import ListClass as ls

#Class Stack
class Stack(object):
    #Initialization
    def __init__(self):
        self._stack=ls.List()

    def push(self,value):
        self._stack.addToHead(value)

    #Get the top of the stack
    def top(self):
        if(self.isStackEmpty()):
            print "The stack is empty"
            return None
        return self._stack.getHeadData()

    def pop(self):
        if(self.isStackEmpty()):
            print "The stack is empty"
            return None
        head=self.top()
        self._stack.remove(self._stack.getHeadData())
        return head
    
    def isStackEmpty(self):
        return self._stack.isEmpty()

    def printStack(self):
        if(self.isStackEmpty()):
            print "The stack is empty"
            return
        self._stack.printList()

    def stackSize(self):
        return self._stack.listSize()