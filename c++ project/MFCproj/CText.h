#pragma once
#include "CShape.h"
class CText : public CShape
{
public:
       DECLARE_SERIAL(CText)
       CText(CString str);
       CText() {};
       ~CText();
       void myShapeDraw (CDC *dc);
       void Serialize(CArchive& archive);
	   void rearrangeStartEndPoints ();
 
private:
       CString text;
};