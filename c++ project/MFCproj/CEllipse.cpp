#include "stdafx.h"
#include "CEllipse.h"
 
IMPLEMENT_SERIAL(CEllipse, CShape, 1)
 
CEllipse::CEllipse(void)
{	
	brushEnabled=true;
}
 
 
CEllipse::~CEllipse(void)
{
}
 
void CEllipse::myShapeDraw(CDC *dc)
{
    dc->Ellipse(startP.x,startP.y,endP.x,endP.y);
}
 
 