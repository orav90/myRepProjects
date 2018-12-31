#include "StdAfx.h"
#include "CChangeColorCommand.h"
 
 
CChangeColorCommand::CChangeColorCommand(CTypedPtrArray<CObArray, CShape*> &shapesArray, CShape *s, COLORREF color)
       : shapesArray(shapesArray), shape(s), color (color)
{
}
 
 
CChangeColorCommand::~CChangeColorCommand(void)
{
}
 
void CChangeColorCommand::perform()
{
       for (int i = 0; i < shapesArray.GetSize(); ++i)
       {
              if (shapesArray[i] == shape)
              {
                     oldColor = shapesArray.GetAt(i)->getColor();
                     shapesArray.GetAt(i)->setColor(color);
                     return;
              }
       }
}
 
void CChangeColorCommand::undo()
{
       for (int i = 0; i < shapesArray.GetSize(); ++i)
       {
              if (shapesArray[i] == shape)
              {
                     shapesArray.GetAt(i)->setColor(oldColor);
                     return;
              }
       }
}