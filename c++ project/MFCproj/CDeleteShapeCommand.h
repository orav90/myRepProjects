#pragma once
#include "CCommand.h"
#include "CShape.h"
 
class CDeleteShapeCommand : public CCommand
{
public:
       CDeleteShapeCommand(CTypedPtrArray<CObArray, CShape*> &shapesArray, CShape *s);
       ~CDeleteShapeCommand(void);
       virtual void perform();
       virtual void undo();
private:
       CTypedPtrArray<CObArray,CShape*> &shapesArray;
       CShape *delShape;
};
 