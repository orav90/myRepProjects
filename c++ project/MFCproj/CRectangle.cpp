#include "stdafx.h"
#include "CRectangle.h"
IMPLEMENT_SERIAL(CRectangle, CShape, 1)
 
CRectangle::CRectangle(void)
{
	brushEnabled=true;
}
 
 
CRectangle::~CRectangle(void)
{
}
 
 
void CRectangle::myShapeDraw(CDC *dc)
{
       dc->Rectangle(startP.x,startP.y,endP.x,endP.y);
}
 