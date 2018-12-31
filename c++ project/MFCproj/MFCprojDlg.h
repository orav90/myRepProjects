#pragma once
#include "afxwin.h"
#include "afxcolorbutton.h"
#include "CShape.h"
#include "CCommand.h"
 
using namespace std;
 
#include <vector>
#include <stack>
#include "afxcmn.h"
 
// CMFCprojDlg dialog
class CMFCprojDlg : public CDialogEx
{
 
       // Construction
public:
       CMFCprojDlg(CWnd* pParent = NULL); // standard constructor
 
       // Dialog Data
       enum { IDD = IDD_MFCPROJ_DIALOG };
 
protected:
       virtual void DoDataExchange(CDataExchange* pDX);       // DDX/DDV support
 
 
       // Implementation
protected:
       HICON m_hIcon;
 
       // Generated message map functions
       virtual BOOL OnInitDialog();
       afx_msg void OnSysCommand(UINT nID, LPARAM lParam);
       afx_msg void OnPaint();
       afx_msg HCURSOR OnQueryDragIcon();
       DECLARE_MESSAGE_MAP()
public:
       CComboBox comboBoxShapeType;
       CComboBox brushTypeCombo;
 
       afx_msg void OnLButtonDown(UINT nFlags, CPoint point);
 
private:
       bool isPressed;
       CTypedPtrArray<CObArray, CShape*> shapes;
       stack<CCommand*> doCommands, undoCommands;
       int selectedShapeInd;
       CToolBar toolbar;
       bool isInMotion;
       UINT timerNum;
 
       CBitmap *screenBmp;
 
public:
       afx_msg void OnMouseMove(UINT nFlags, CPoint point);
       afx_msg void OnLButtonUp(UINT nFlags, CPoint point);
       CMFCColorButton colorType;
       afx_msg void OnBnClickedButtonLoadPicture();
       afx_msg void OnContextMenu(CWnd* /*pWnd*/, CPoint /*point*/);
       afx_msg void OnPopupColor();
       afx_msg void OnPopupDelete();
       void OnOpenFile ();
       void OnSaveFile ();
       void OnUndo ();
       void OnRedo ();
       void OnNew ();
       void clearAll ();
       void OnRedraw ();
       afx_msg void OnSize(UINT nType, int cx, int cy);
       afx_msg void OnTimer(UINT_PTR nIDEvent);
       afx_msg void OnClickedCheckMotion();
       void setBitMap ();
 
       CSliderCtrl motionRateSlider;
       int motionRate;
       afx_msg void OnCustomdrawMotionRateSlider(NMHDR *pNMHDR, LRESULT *pResult);
       RECT getBlockingRectangle (CPoint startP, CPoint endP);
       afx_msg void OnPopupBrush();
       afx_msg BOOL OnEraseBkgnd(CDC* pDC);
       CEdit editText;
       afx_msg void OnKillfocusEditText();
	   void OnOK ();	
	   void OnCancel ();
};
 