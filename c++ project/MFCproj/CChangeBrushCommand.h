#pragma once
#include "CCommand.h"
#include "CShape.h"
 
class CChangeBrushCommand : public CCommand
{
public:
       CChangeBrushCommand(CTypedPtrArray<CObArray, CShape*> &shapesArray, CShape *s, CShape::BRUSH_TYPE brushType);
       ~CChangeBrushCommand(void);
       virtual void perform();
       virtual void undo();
private:
       CTypedPtrArray<CObArray,CShape*> &shapesArray;
       CShape *shape;
       CShape::BRUSH_TYPE brushType;
       CShape::BRUSH_TYPE oldBrushType;
};