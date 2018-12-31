#include "StdAfx.h"
#include "CAddShapeCommand.h"
 
 
CAddShapeCommand::CAddShapeCommand(CTypedPtrArray<CObArray, CShape*> &shapesArray, CShape *s)
       : shapesArray(shapesArray), newShape(s)
{
}
 
 
CAddShapeCommand::~CAddShapeCommand(void)
{
}
 
void CAddShapeCommand::perform()
{
       shapesArray.Add(newShape);
}
 
void CAddShapeCommand::undo()
{
     for (int i = 0; i < shapesArray.GetSize(); ++i)
	 {
         if (shapesArray[i] == newShape)
         {
             shapesArray.RemoveAt(i);
             return;
         }
	 }
}
