#include "stdafx.h"
#include "CText.h"
 
IMPLEMENT_SERIAL(CText, CShape, 1)
 
CText::CText(CString str)
{
       text= str;
}
 
CText::~CText(void)
{
}
 
void CText::myShapeDraw(CDC *dc)
{
       CFont curFont, *oldFont;
       curFont.CreateFontW (48,20,0,0,FW_DONTCARE,FALSE,FALSE,FALSE,DEFAULT_CHARSET,OUT_OUTLINE_PRECIS,CLIP_DEFAULT_PRECIS,CLEARTYPE_QUALITY,VARIABLE_PITCH,_T("Arial"));
       oldFont = dc->SelectObject(&curFont);
       dc->SetTextColor(curColor);
       dc->SetBkMode(TRANSPARENT);
       CRect r(startP,endP);
       dc->DrawText(text,r,DT_SINGLELINE|DT_NOCLIP);
       dc->SelectObject(&oldFont);
}
 
void CText::Serialize(CArchive& archive)
{
       CShape::Serialize(archive);
       if(archive.IsStoring())
       {
              archive << text;
       }
       else
       {
              archive >> text;
       }
}
 
void CText::rearrangeStartEndPoints ()
{
	if ((startP.x>endP.x) || (startP.y>endP.y))
	{
		CPoint tempP = startP;
		startP= endP;
		endP=tempP;
	}
}