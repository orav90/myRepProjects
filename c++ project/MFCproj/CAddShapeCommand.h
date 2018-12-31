#pragma once
#include "CCommand.h"
#include "CShape.h"
 
class CAddShapeCommand : public CCommand
{
public:
       CAddShapeCommand(CTypedPtrArray<CObArray, CShape*> &shapesArray, CShape *s);
       ~CAddShapeCommand(void);
       virtual void perform();
       virtual void undo();
private:
       CTypedPtrArray<CObArray,CShape*> &shapesArray;
       CShape *newShape;
};