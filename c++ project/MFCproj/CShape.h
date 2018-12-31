#pragma once
#include "afxwin.h"
#include "resource.h"
 
class CShape : public CObject
{
public:
       DECLARE_SERIAL(CShape)
 
       enum MOVEMENT_TYPE
       {    
              HORIZONTAL,
              VERTICAL,
              DIAGONAL
       };
		
	   enum BRUSH_TYPE
	   {
		   SOLID_BRUSH,
		   HATCH_BRUSH,
		   LOVE_BRUSH
	   };

	   CShape(void);
       virtual ~CShape(void);
       virtual void draw(CDC *dc);
       virtual void myShapeDraw(CDC *dc) {}
       void setStartPoint(CPoint);
       void setEndPoint(CPoint);
       void setColor(COLORREF);
       COLORREF getColor() const;
       CPoint getEndPoint() const;
       CPoint getStartPoint() const;
       virtual void Serialize(CArchive& archive);
       void setMovementType (MOVEMENT_TYPE moveType);
       virtual void move (CDC *dc);
	   void setBrushType(BRUSH_TYPE);
	   BRUSH_TYPE getBrushType()const;
	   bool isBrushEnabled () const;
 
       virtual void rearrangeStartEndPoints ();
	   void selectBrush(CBrush *myBrush);
	 
protected:
       COLORREF curColor;
       CPoint startP;
       CPoint endP;
       MOVEMENT_TYPE movementType;
       int xDirectionStep;
       int yDirectionStep;
	   BRUSH_TYPE brushType;	
	   bool brushEnabled;
};
 