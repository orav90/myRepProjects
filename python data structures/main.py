import ListClass as ls
import StackClass as sc
import HashTableClass as ht
import BinarySearchTreeClass as bst
import QueueClass as qc
import numpy as np


# x=ls.List()
# x.addToTail(1)
# x.addToTail(3)
# x.addToTail(5)
# x.addAfter(5,7)
# x.addToHead(0)
# x.printList()
# print x.searchNode(3)
# x.remove(3)
# x.printList()
# print x.searchNode(3)


s=sc.Stack()
s.push(4)
s.push(1)
s.push(5)
s.push(3)
s.pop()
s.isStackEmpty()
s.printStack()



q=qc.Queue()
q.enqueue(3)
q.enqueue(9)
q.enqueue(8)
q.dequeue()
print q.front()
q.enqueue(6)
print q.isQueueEmpty()
q.printQueue()





c=ht.HashTable(10)
c.insert(24,"Jack")
c.insert(23,"Bob")
c.insert(8,"Alise")
c.insert(18,"Cat")
c.insert(38,"Dog")
c.insert(88,"Fish")
c.insert(313,"Cow")
c.insert(19,"Sheep")
c.printHashTable()
print c.getValue(19)
c.deleteKey(19)
c.printHashTable()



c=bst.BinarySearchTree()
c.insert(20)
c.insert(10)
c.insert(30)
c.insert(5)
c.insert(25)
c.insert(50)

c.printInOrder(c.root)
print c.searchNode(10)
c.deleteNode(20)
c.deleteNode(5)
print c.countNodes(c.root)
c.printInOrder(c.root)





























