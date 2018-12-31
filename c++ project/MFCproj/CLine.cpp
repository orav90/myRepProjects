#include "stdafx.h"
#include "CLine.h"
 
IMPLEMENT_SERIAL(CLine, CShape, 1)
 
CLine::CLine(void)
{
}	
 
CLine::~CLine(void)
{
}
 
void CLine::myShapeDraw(CDC *dc)
{
       dc->MoveTo(startP.x, startP.y);
       dc->LineTo(endP.x, endP.y);
}

void CLine::rearrangeStartEndPoints ()
{
	if ((startP.x>endP.x) || (startP.y>endP.y))
	{
		CPoint tempP = startP;
		startP= endP;
		endP=tempP;
	}
}
