#include "StdAfx.h"
#include "CChangeBrushCommand.h"
 
 
CChangeBrushCommand::CChangeBrushCommand(CTypedPtrArray<CObArray, CShape*> &shapesArray, CShape *s, CShape::BRUSH_TYPE brushType)
       : shapesArray(shapesArray), shape(s), brushType(brushType)
{
}
 
 
CChangeBrushCommand::~CChangeBrushCommand(void)
{
}
 
void CChangeBrushCommand::perform()
{
       for (int i = 0; i < shapesArray.GetSize(); ++i)
       {
              if (shapesArray[i] == shape)
              {
                     oldBrushType = shapesArray.GetAt(i)->getBrushType();
                     shapesArray.GetAt(i)->setBrushType(brushType);
                     return;
              }
       }
}
 
void CChangeBrushCommand::undo()
{
       for (int i = 0; i < shapesArray.GetSize(); ++i)
       {
              if (shapesArray[i] == shape)
              {
                     shapesArray.GetAt(i)->setBrushType(oldBrushType);
                     return;
              }
       }
}