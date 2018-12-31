#pragma once
#include "CCommand.h"
#include "CShape.h"
 
class CChangeColorCommand : public CCommand
{
public:
       CChangeColorCommand(CTypedPtrArray<CObArray, CShape*> &shapesArray, CShape *s, COLORREF color);
       ~CChangeColorCommand(void);
       virtual void perform();
       virtual void undo();
private:
       CTypedPtrArray<CObArray,CShape*> &shapesArray;
       CShape *shape;
       COLORREF color;
       COLORREF oldColor;
};