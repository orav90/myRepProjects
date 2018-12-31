#include "stdafx.h"
#include "CShape.h"
 
IMPLEMENT_SERIAL(CShape, CObject, 1)
 
#define TOP_BORDER_Y 30
CShape::CShape(void)
{
       movementType=HORIZONTAL;
       xDirectionStep=1;
       yDirectionStep=0;
	   brushType = SOLID_BRUSH;
	   brushEnabled= false;
}
 
bool CShape::isBrushEnabled () const
{
	return brushEnabled;
}
 
CShape::~CShape(void)
{
}
 
void CShape::draw(CDC *dc)
{
       CPen pen;
       pen.CreatePen(PS_SOLID,3,curColor);
       CPen *oldPen=dc->SelectObject(&pen);
	   CBrush *oldBrush;
	   CBrush myBrush;
	   selectBrush(&myBrush);
	   oldBrush =dc->SelectObject (&myBrush);
       myShapeDraw (dc);
 
       dc->SelectObject(oldPen);
   
	   dc->SelectObject(oldBrush);
}
 
void CShape::Serialize( CArchive& archive )
{
       // call base class function first
       // base class is CObject in this case
       CObject::Serialize( archive );
	   int brush, moveDirection;
	   // now do the stuff for our specific class
       if(archive.IsStoring())
	   {
		     brush = (int)CShape::brushType;
			 moveDirection = (int)movementType;
             archive << startP.x << startP.y << endP.x << endP.y << curColor << brush << moveDirection << xDirectionStep << yDirectionStep;
	   }
       else
	   {
		    int brush;
            archive >> startP.x >> startP.y >> endP.x >> endP.y >> curColor >> brush >> moveDirection >> xDirectionStep >> yDirectionStep;
			brushType = (BRUSH_TYPE)brush;
			movementType = (MOVEMENT_TYPE)moveDirection;
	   }
}
 
void CShape::setStartPoint(CPoint currStartP)
{
       startP=currStartP;
}
 
void CShape::setEndPoint(CPoint currEndP)
{
       endP=currEndP;
}
 
void CShape::setColor(COLORREF tCurColor)
{
       curColor=tCurColor;
}
 
COLORREF CShape::getColor() const
{
       return curColor;
}
 
CPoint CShape::getEndPoint() const
{
       return endP;
}
 
CPoint CShape::getStartPoint() const
{
       return startP;
}
 
void CShape::setMovementType (MOVEMENT_TYPE moveType)
{
       movementType=moveType;
       switch (movementType)
       {
              case HORIZONTAL:
              {
                     xDirectionStep=1;
                     yDirectionStep=0;
                     break;
              }
              case VERTICAL:
              {
                     xDirectionStep=0;
                     yDirectionStep=1;
                     break;
              }
              case DIAGONAL:
              {
                     xDirectionStep=1;
                     yDirectionStep=1;
                     break;
              }
 
       }
 
}
 
void CShape::move (CDC *dc)
{
       CRect rect;
 
       dc->GetWindow()->GetClientRect(rect);
 
       int y = rect.BottomRight().y;
 
       if (movementType==HORIZONTAL)
       {
              if (endP.x > rect.BottomRight().x)
              {
                     xDirectionStep=-1;
              }
              else if (startP.x<=0)
              {
                     xDirectionStep=1;
              }
       }
       else if (movementType==VERTICAL)
       {
              if (endP.y > rect.BottomRight().y)
              {
                     yDirectionStep=-1;
              }
              else if (startP.y<=TOP_BORDER_Y)
              {
                     yDirectionStep=1;
              }
       }
       else
       {
              if (endP.x > rect.BottomRight().x)
              {
                     xDirectionStep=-1;
              }
              else if (startP.x<=0)
              {
                     xDirectionStep=1;
              }
              if (endP.y > rect.BottomRight().y)
              {
                     yDirectionStep=-1;
              }
              else if (startP.y<=TOP_BORDER_Y)
              {
                     yDirectionStep=1;
              }
       }
       startP.x+=xDirectionStep;
       startP.y+=yDirectionStep;
       endP.x+=xDirectionStep;;
       endP.y+=yDirectionStep;
}
 
// Arrange the start and end points, make sure that startP < endP
void CShape::rearrangeStartEndPoints ()
{
       CPoint tempStartP, tempEndP;
       if ((endP.x<startP.x) || (endP.y < startP.y))
       {
              tempStartP.x = (startP.x<endP.x)?startP.x:endP.x; 
              tempStartP.y = (startP.y<endP.y)?startP.y:endP.y;
 
              tempEndP.x = (startP.x>endP.x)?startP.x:endP.x; 
              tempEndP.y = (startP.y>endP.y)?startP.y:endP.y;
 
              startP = tempStartP;  
              endP   = tempEndP;
       }
}
 
void CShape::setBrushType(BRUSH_TYPE brush)
{
	brushType=brush;
}

CShape::BRUSH_TYPE CShape::getBrushType()const
{
	return brushType;
}

void CShape::selectBrush(CBrush *myBrush)
{	
	switch(brushType)
	{
	   case SOLID_BRUSH:
	   {
		   // CBrush brush;
			myBrush->CreateSolidBrush (curColor);
			break;
	   }
	   case HATCH_BRUSH:
	   {
			myBrush->CreateHatchBrush(HS_DIAGCROSS, curColor);
			break;
	   }
	   case LOVE_BRUSH:
	   {
		   CBitmap bmp;
		   bmp.LoadBitmapW(IDB_BITMAP_LOVE);
		   myBrush->CreatePatternBrush (&bmp);
		   break;
	   }
      
   }
 
}