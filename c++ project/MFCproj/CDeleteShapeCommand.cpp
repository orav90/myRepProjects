#include "StdAfx.h"
#include "CDeleteShapeCommand.h"
 
 
CDeleteShapeCommand::CDeleteShapeCommand(CTypedPtrArray<CObArray, CShape*> &shapesArray, CShape *s)
       : shapesArray(shapesArray), delShape(s)
{
}
 
 
CDeleteShapeCommand::~CDeleteShapeCommand(void)
{
}
 
void CDeleteShapeCommand::perform()
{
       for (int i = 0; i < shapesArray.GetSize(); ++i)
       {
              if (shapesArray[i] == delShape)
              {
                     shapesArray.RemoveAt(i);
                     return;
              }
       }
}
 
void CDeleteShapeCommand::undo()
{
       shapesArray.Add (delShape);
}
 