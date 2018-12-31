
#Class TreeNode
class TreeNode:
    def __init__(self,data):
        self._data=data
        self._right=None
        self._left=None
        self._parent=None

    def getData(self):
        return self._data

    def setData(self,newData):
        self._data=newData

    def getRight(self):
        return self._right

    def setRight(self,newRight):
        self._right=newRight

    def getLeft(self):
        return self._left

    def setLeft(self,newLeft):
        self._left=newLeft


#Class BST
class BinarySearchTree:
    #Initialization
    def __init__(self):
        self.root = None

    def getRoot(self):
        return self.root

    #insert data to the tree
    def insert(self, data):
        #insert to tree only if the key is int or float
        if (isinstance(data,int) or isinstance(data,float)):

            if(self.root)==None:
                self.root=TreeNode(data)
            else:
                self._insertNode(self.root,data)
        else:
            print "Insert to BinarySearchTree failed, illegal data"

    #Insert node to the tree in the correct position to ensure binary search tree
    def _insertNode(self,currNode,data):
        #if new data is smaller than node's data go left
        if data < currNode._data:
            if currNode._left == None:
                newNode=TreeNode(data)
                newNode._parent=currNode
                currNode._left = newNode

            else:
                self._insertNode(currNode._left,data)


        else:
            # if new data is bigger than node's data go right
            if currNode._right == None:
                newNode=TreeNode(data)
                newNode._parent=currNode
                currNode._right = newNode
            else:
                self._insertNode(currNode._right,data)


    def printPreOrder(self,currNode):
        if currNode != None:
            print currNode._data
            self.printPreOrder(currNode._left)
            self.printPreOrder(currNode._right)

    def printInOrder(self,currNode=None):
        if currNode != None:
            self.printInOrder(currNode._left)
            print currNode._data
            self.printInOrder(currNode._right)

    def printPostOder(self,currNode):
        if(currNode!=None):
            self.printPreOrder(currNode._left)
            self.printPreOrder(currNode._right)
            print currNode._data

    #Count number of nodes
    def countNodes(self,currNode, count=0):

        if currNode == None:
            return count

        return self.countNodes(currNode._left, self.countNodes(currNode._right, count + 1))

    #Search data in the tree
    def searchNode(self,data):
        foundNode=self._search(data,self.root)
        if(foundNode==None):
            return False
        else:
            return True

    def _search(self,data,currNode):
        if(currNode==None):
            return None
        elif (data==currNode._data):
            return currNode
        elif (currNode._data>data):
            return self._search(data,currNode._left)
        else:
            return self._search(data,currNode._right)

    #return max value of the tree
    def getMax(self):
        return self._treeMax()._data

    def _treeMax(self,currNode=None):
        if (currNode==None):
            currNode=self.root
        while(currNode._right!=None):
            currNode=currNode._right
        return currNode

    #return min value of the tree
    def getMin(self):
        return self._treeMin()._data

    def _treeMin(self,currNode=None):
        if (currNode==None):
            currNode=self.root
        while(currNode._left!=None):
            currNode=currNode._left
        return currNode

    def _successor(self,currNode):
        if(currNode._right!=None):
            return self._treeMin(currNode._right)

        p=currNode._parent
        while p!=None and currNode==p._right:
            currNode=p
            p=p._parent
        return p

    #Delete Node from the tree
    def deleteNode(self,data):

        #Search the data in the tree
        currNode=self._search(data,self.root)
        if(currNode==None):
            return
        succ=None
        x=None
        #if one of the sons(left or right) is missing save
        if(currNode._right==None or currNode._left==None):
            succ=currNode
        else:
            #Get the successor of the deleted node
            succ=self._successor(currNode)
            currNode._data=succ._data

        if(succ._left!=None):
            x=succ._left
        else:
            x=succ._right
        if(x!=None):
            #set the parent of the son(x) to be its grandfather(parent of the deleted node)
            x._parent=succ._parent

        if(succ._parent!=None):
            #if the successor is the left son of its parent
            if (succ==succ._parent._left):
                succ._parent._left=x

            #if the successor is the right son of its parent
            else:
                succ._parent._right = x
        else:
            self.root=x

        return succ
