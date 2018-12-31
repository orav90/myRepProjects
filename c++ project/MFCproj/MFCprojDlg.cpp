// MFCprojDlg.cpp : implementation file
//
#include "stdafx.h"
#include "MFCproj.h"
#include "MFCprojDlg.h"
#include "afxdialogex.h"
#include "CEllipse.h"
#include "CRectangle.h"
#include "CLine.h"
#include "CText.h"
#include "CAddShapeCommand.h"
#include "CDeleteShapeCommand.h"
#include "CChangeColorCommand.h"
#include "CChangeBrushCommand.h"
#include <exception>
#define MAX_SHAPES_TYPE 4
 
#ifdef _DEBUG
#define new DEBUG_NEW
#endif
 
#define WIDTHBYTES(bits) ((((bits) + 31) / 32) * 4)
 
class CAboutDlg : public CDialogEx
{
public:
       CAboutDlg();
       // Dialog Data
       enum { IDD = IDD_ABOUTBOX };
protected:
       virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support
       // Implementation
protected:
       DECLARE_MESSAGE_MAP()
public:
};
CAboutDlg::CAboutDlg() : CDialogEx(CAboutDlg::IDD)
{
}
void CAboutDlg::DoDataExchange(CDataExchange* pDX)
{
       CDialogEx::DoDataExchange(pDX);
}
BEGIN_MESSAGE_MAP(CAboutDlg, CDialogEx)
END_MESSAGE_MAP()
 
// CMFCprojDlg dialog
enum SHAPE_TYPE
{   
       ELLIPSE,
       LINE,
       RECTANGLE,
       TEXT
};
 
CMFCprojDlg::CMFCprojDlg(CWnd* pParent /*=NULL*/)
       : CDialogEx(CMFCprojDlg::IDD, pParent)
{
       m_hIcon = AfxGetApp()->LoadIcon(IDR_MAINFRAME);
       isPressed=false;
       selectedShapeInd=-1;
       isInMotion = false;
       motionRate = 5;
}
void CMFCprojDlg::DoDataExchange(CDataExchange* pDX)
{
       CDialogEx::DoDataExchange(pDX);
       DDX_Control(pDX, IDC_COMBO_SHAPE_TYPE, comboBoxShapeType);
       DDX_Control(pDX, IDC_MFCCOLORBUTTON1, colorType);
       DDX_Control(pDX, IDC_MOTION_RATE_SLIDER, motionRateSlider);
       DDX_Control(pDX, IDC_COMBO_BRUSH_TYPE, brushTypeCombo);
       DDX_Control(pDX, IDC_EDIT_TEXT, editText);
}
BEGIN_MESSAGE_MAP(CMFCprojDlg, CDialogEx)
       ON_WM_SYSCOMMAND()
       ON_WM_PAINT()
       ON_WM_QUERYDRAGICON()
       ON_WM_LBUTTONDOWN()
       ON_WM_MOUSEMOVE()
       ON_WM_LBUTTONUP()
       ON_WM_CONTEXTMENU()
       ON_COMMAND(ID_POPUP_COLOR, &CMFCprojDlg::OnPopupColor)
       ON_COMMAND(ID_POPUP_DELETE, &CMFCprojDlg::OnPopupDelete)
       ON_COMMAND(ID_NEW, &CMFCprojDlg::OnNew)
       ON_COMMAND(ID_OPEN_FILE, &CMFCprojDlg::OnOpenFile)
       ON_COMMAND(ID_SAVE_FILE, &CMFCprojDlg::OnSaveFile)
       ON_COMMAND(ID_UNDO, &CMFCprojDlg::OnUndo)
       ON_COMMAND(ID_REDO, &CMFCprojDlg::OnRedo)
       ON_COMMAND(ID_REDRAW, &CMFCprojDlg::OnRedraw)
       ON_WM_SIZE()
       ON_WM_TIMER()
       ON_BN_CLICKED(IDC_CHECK_MOTION, &CMFCprojDlg::OnClickedCheckMotion)
       ON_NOTIFY(NM_CUSTOMDRAW, IDC_MOTION_RATE_SLIDER, &CMFCprojDlg::OnCustomdrawMotionRateSlider)
       ON_COMMAND(ID_POPUP_BRUSH, &CMFCprojDlg::OnPopupBrush)
       ON_WM_ERASEBKGND()
//     ON_WM_GETDLGCODE()
//ON_WM_KILLFOCUS()
ON_EN_KILLFOCUS(IDC_EDIT_TEXT, &CMFCprojDlg::OnKillfocusEditText)
END_MESSAGE_MAP()
 
// CMFCprojDlg message handlers
BOOL CMFCprojDlg::OnInitDialog()
{
       CDialogEx::OnInitDialog();
       // Add "About..." menu item to system menu.
       // IDM_ABOUTBOX must be in the system command range.
       ASSERT((IDM_ABOUTBOX & 0xFFF0) == IDM_ABOUTBOX);
       ASSERT(IDM_ABOUTBOX < 0xF000);
       CMenu* pSysMenu = GetSystemMenu(FALSE);
       if (pSysMenu != NULL)
       {
              BOOL bNameValid;
              CString strAboutMenu;
              bNameValid = strAboutMenu.LoadString(IDS_ABOUTBOX);
              ASSERT(bNameValid);
              if (!strAboutMenu.IsEmpty())
              {
                     pSysMenu->AppendMenu(MF_SEPARATOR);
                     pSysMenu->AppendMenu(MF_STRING, IDM_ABOUTBOX, strAboutMenu);
              }
       }
       // Set the icon for this dialog.  The framework does this automatically
       //  when the application's main window is not a dialog
       SetIcon(m_hIcon, TRUE);                  // Set big icon
       SetIcon(m_hIcon, FALSE);          // Set small icon
       // TODO: Add extra initialization here
       CString shapesNames[MAX_SHAPES_TYPE];
       shapesNames[0]=_T("Ellipse");
       shapesNames[1]=_T("Line");
       shapesNames[2]=_T("Rectangle");
       shapesNames[3]=_T("Text");
       // Set the data of each item to be equal to its index.
       for (int i = 0; i < MAX_SHAPES_TYPE; i++)
       {
              comboBoxShapeType.AddString(shapesNames[i]);
       }
       comboBoxShapeType.SetCurSel(0);
 
       CString brushType[3];
       brushType[0]=_T("Solid");
       brushType[1]=_T("Hatch");
       brushType[2]=_T("Love");
       for(int i=0; i<3;i++)
       {
              brushTypeCombo.AddString(brushType[i]);
       }
       brushTypeCombo.SetCurSel(0);
       // Create tool bar
       toolbar.Create(this);
       toolbar.LoadToolBar(IDR_MAINFRAME_TOOLBAR);
       toolbar.ShowWindow(SW_SHOW);
       toolbar.SetBarStyle(CBRS_ALIGN_TOP |CBRS_TOOLTIPS | CBRS_FLYBY);
       RepositionBars(AFX_IDW_CONTROLBAR_FIRST, AFX_IDW_CONTROLBAR_LAST,0);
 
       // Create slider for the motion 
       motionRateSlider.SetRange(1,50,TRUE);
       motionRateSlider.SetPos(25);
 
	   // Set colors for the bitmap - define color pallete
       setBitMap ();
 
       return TRUE;  // return TRUE  unless you set the focus to a control
}
void CMFCprojDlg::OnSysCommand(UINT nID, LPARAM lParam)
{
       if ((nID & 0xFFF0) == IDM_ABOUTBOX)
       {
              CAboutDlg dlgAbout;
              dlgAbout.DoModal();
       }
       else
       {
              CDialogEx::OnSysCommand(nID, lParam);
       }
}

void CMFCprojDlg::OnPaint()
{  
       CRect r;
 
       GetClientRect (r);
       CPaintDC dc(this); // device context for painting
       CDC dcMem;

       // create compatible memory DC
       dcMem.CreateCompatibleDC(&dc);

	   // Select screenBmp
       CBitmap *oldBmp = dcMem.SelectObject(screenBmp);  

	   // Fill a rectangle in the screen size
	   dcMem.FillSolidRect (r,dcMem.GetBkColor());
 
	   // draw to the alternative dcMem (second buffer)
       for(int i=0;i<shapes.GetSize();i++)
       {
              if (shapes[i] != NULL)
              {
				  // Polymorphism - each shape draws itself
                     shapes[i]->draw(&dcMem);
              }
       }
 
	   // Display the dcMem buffer, after all the shapes were drawn
       dc.BitBlt(r.left,r.top,r.Width(),r.Height(), &dcMem, r.left,r.top, SRCCOPY);
 
	   // Select the old buffer
       dcMem.SelectObject(oldBmp);
       CDialogEx::OnPaint();
}
// The system calls this function to obtain the cursor to display while the user drags
//  the minimized window.
HCURSOR CMFCprojDlg::OnQueryDragIcon()
{
       return static_cast<HCURSOR>(m_hIcon);
}
 
void CMFCprojDlg::OnLButtonDown(UINT nFlags, CPoint point)
{
       isPressed=true;
       SHAPE_TYPE shapeTypeInd=(SHAPE_TYPE)comboBoxShapeType.GetCurSel();
       CShape *newShape;

	   // Create the shapes according to shape type
       switch(shapeTypeInd)
       {
       case ELLIPSE:
              {
                     newShape = new CEllipse ();
                     newShape->setMovementType (CShape::VERTICAL);
                     newShape->setBrushType((CShape::BRUSH_TYPE)brushTypeCombo.GetCurSel());
                     break;
              }
       case  LINE:
              {
                     newShape = new CLine ();
                     newShape->setMovementType (CShape::HORIZONTAL);
                     break;
              }
       case  RECTANGLE:
              {
                     newShape = new CRectangle ();
                     newShape->setMovementType (CShape::DIAGONAL);
                     newShape->setBrushType((CShape::BRUSH_TYPE)brushTypeCombo.GetCurSel());
                     break;
              }
       case  TEXT:
              {
                     CString str = _T("");
                     editText.GetWindowTextW(str);
                     newShape = new CText (str);
                     newShape->setMovementType (CShape::DIAGONAL);
                     break;
              }
       }
 
       newShape->setStartPoint(point);
       newShape->setColor(colorType.GetColor());
 
	   // Create a new add command 
       CCommand *c = new CAddShapeCommand (shapes, newShape);
	   // Save the new shape in shapes array
       c->perform();
       doCommands.push(c);
 
       CDialogEx::OnLButtonDown(nFlags, point);
}
 
void CMFCprojDlg::OnMouseMove(UINT nFlags, CPoint point)
{
       if(isPressed)
       {
              shapes[shapes.GetSize()- 1]->setEndPoint(point);
	
			  // Order start and end points, and enlarge the blocking rectangle a bit
              RECT r = getBlockingRectangle (shapes[shapes.GetSize()- 1]->getStartPoint(), shapes[shapes.GetSize()- 1]->getEndPoint());
			 
			  // InvalidateRect - refreshes the current shape only
              InvalidateRect(&r,true);      
       }
       CDialogEx::OnMouseMove(nFlags, point);
 
}
 
void CMFCprojDlg::OnLButtonUp(UINT nFlags, CPoint point)
{
       isPressed=false;
       CPoint tempP;
 
	   // When the button is up - drawing of the shape ended. Save end point
       CPoint startP = shapes[shapes.GetSize() - 1]->getStartPoint();
       shapes[shapes.GetSize() - 1]->setEndPoint(point);

	   // Rearrange start and end points
       shapes[shapes.GetSize() - 1]->rearrangeStartEndPoints ();

       CDialogEx::OnLButtonUp(nFlags, point);
 
}
 
 
void CMFCprojDlg::OnContextMenu(CWnd* pWnd, CPoint point)
{
       CRect r;
       BOOL isHit=false;
       int i = shapes.GetSize() - 1;
 
       // Convert hit point from screen coordinates to divide coordinates
       CPoint pos(point);
       ScreenToClient(&pos);
 
	   // Find the shape which is pointed by the cursor.
	   // Start from the last shape drawn - in order to select the top shape first 
       while((!isHit) && (i>=0))
       {
              if(shapes[i] != NULL)
              {
                     CPoint startP = shapes[i]->getStartPoint();
                     CPoint endP = shapes[i]->getEndPoint();
                     r.left=(startP.x<endP.x)?startP.x:endP.x;         
                     r.top=(startP.y<endP.y)?startP.y:endP.y;   
                     r.right=(startP.x>endP.x)?startP.x:endP.x;        
                     r.bottom=(startP.y>endP.y)?startP.y:endP.y; 
                     isHit=PtInRect(r,pos);
                     if (isHit)
                     {
                           selectedShapeInd = i;
                     }
              }
              i--;
       }
 
       if(isHit)
       {
             // Display contents menu
              CMenu menu;
              menu.LoadMenu (IDR_POPUP_SHAPE_PROP);
	          CMenu * pPopup;

              pPopup = menu.GetSubMenu (0);

              // Disable brush
              if (!shapes[selectedShapeInd]->isBrushEnabled())
              {
                     pPopup->EnableMenuItem (ID_POPUP_BRUSH,  MF_DISABLED | MF_GRAYED );
              }     

              pPopup->TrackPopupMenu (TPM_LEFTALIGN | TPM_RIGHTBUTTON, point.x, point.y, this);

       }

}


void CMFCprojDlg::OnPopupColor()
{
       CCommand *c = new CChangeColorCommand (shapes, shapes.GetAt (selectedShapeInd), colorType.GetColor());
       c->perform();
       doCommands.push(c);
       Invalidate();
}

void CMFCprojDlg::OnPopupBrush()
{
       CCommand *c = new CChangeBrushCommand (shapes, shapes.GetAt (selectedShapeInd), (CShape::BRUSH_TYPE)brushTypeCombo.GetCurSel());
       c->perform();
       doCommands.push(c);
       Invalidate();
}
 

void CMFCprojDlg::OnPopupDelete()
{
       CCommand *c = new CDeleteShapeCommand (shapes, shapes.GetAt (selectedShapeInd));
       c->perform();
       doCommands.push(c);
       Invalidate();
}

 

void CMFCprojDlg::OnOpenFile ()
{   
       DWORD dwFlags = OFN_HIDEREADONLY | OFN_NOCHANGEDIR | OFN_FILEMUSTEXIST;
       static TCHAR BASED_CODE fileExt[] = _T("Shape Files(*.shp)|*.shp||");
       CFileDialog fOpenDlg(true, NULL,NULL, dwFlags, fileExt);
       if (fOpenDlg.DoModal()==IDOK)
       {
			// Delete all shapes and commands
              clearAll ();
              CFile file(fOpenDlg.GetFileName(), CFile::modeRead);
              CArchive ar(&file, CArchive::load);
              shapes.Serialize(ar);
              Invalidate ();
              ar.Close();
       }
}  

void CMFCprojDlg::OnSaveFile ()
{
       DWORD dwFlags = OFN_HIDEREADONLY | OFN_NOCHANGEDIR;
       static TCHAR BASED_CODE fileExt[] = _T("Shape Files(*.shp)|*.shp||");
       CFileDialog fOpenDlg(false, NULL,NULL, dwFlags, fileExt);
       if (fOpenDlg.DoModal()==IDOK)
       {
              CFile file;
              if(fOpenDlg.GetFileExt() != _T("shp"))
              {
                     file.Open (fOpenDlg.GetFileName() +  _T(".shp"), CFile::modeWrite | CFile::modeCreate);
              }
              else
              {
                     file.Open (fOpenDlg.GetFileName(), CFile::modeWrite | CFile::modeCreate);
              }
              CArchive ar(&file, CArchive::store);
              shapes.Serialize(ar);
              ar.Close();
       }          
}  


void CMFCprojDlg::OnUndo ()
{
       if (!doCommands.empty())
       {
              CCommand *c = doCommands.top();
              c->undo();
              undoCommands.push(c);
              doCommands.pop();
              Invalidate();
       }
}  

void CMFCprojDlg::OnRedo ()
{
      if (!undoCommands.empty())
      {
             CCommand *c = undoCommands.top();
             c->perform();
             doCommands.push(c);
             undoCommands.pop();
             Invalidate();
       }
}  

void CMFCprojDlg::OnNew ()
{
       clearAll ();
       Invalidate ();
}  

void CMFCprojDlg::clearAll ()
{
       CCommand *c;
       while (!undoCommands.empty())
       {
              c = undoCommands.top();
              undoCommands.pop();
              delete c;
       }

       while (!doCommands.empty())
       {
              c = doCommands.top();
              doCommands.pop();
              delete c;
       }


       CShape *shape;
       while (shapes.GetSize() > 0)
       {
              shape = shapes.GetAt(shapes.GetSize()-1);
              shapes.RemoveAt(shapes.GetSize()-1);
              delete shape;
       }
}

// Activated by R button. Force call to onpaint, which draws only the shapes
void CMFCprojDlg::OnRedraw ()
{
       Invalidate();
}

// Maximize
void CMFCprojDlg::OnSize(UINT nType, int cx, int cy)

{
       CDialogEx::OnSize(nType, cx, cy);

	   // Position IDOK and IDCancel on the right corner
       CWnd* pwnd;
       CRect rect, rect1;

       GetClientRect (rect);
       pwnd = GetDlgItem (IDOK);
       if ( pwnd )
       {
              pwnd->GetWindowRect (&rect1);
              pwnd->MoveWindow (rect.right - 180, rect.bottom - 35,
              rect1.Width (), rect1.Height (), TRUE);
       }

       pwnd = GetDlgItem (IDCANCEL);
       if ( pwnd )
       {
              pwnd->GetWindowRect (&rect1);
              pwnd->MoveWindow (rect.right - 90, rect.bottom - 35,
              rect1.Width (), rect1.Height (), TRUE);
       }

	   // The window was resized, set the new bit map
       setBitMap();
}

void CMFCprojDlg::OnTimer(UINT_PTR nIDEvent)
{
       CPaintDC dc(this);
	   RECT r;

       CPoint endP;
       for(int i=0;i<shapes.GetSize();i++)
       {
              if (shapes[i] != NULL)
              {
                     endP = shapes[i]->getEndPoint ();
                     if ((endP.x!=0) || (endP.y!=0))
                     {
                           shapes[i]->move(&dc);
                           CPoint startP = shapes[i]->getStartPoint();
                           CPoint endP   = shapes[i]->getEndPoint();
                           r = getBlockingRectangle (startP, endP);
                           InvalidateRect(&r, true);
                     }

              }

       }

       CDialogEx::OnTimer(nIDEvent);
}

void CMFCprojDlg::OnClickedCheckMotion()
{
       if (!isInMotion)
       {
              timerNum = SetTimer(1, motionRate, 0);
              isInMotion=true;
       }
       else
       {
              KillTimer(timerNum); 
              isInMotion=false;
       }
}



void CMFCprojDlg::OnCustomdrawMotionRateSlider(NMHDR *pNMHDR, LRESULT *pResult)
{
       LPNMCUSTOMDRAW pNMCD = reinterpret_cast<LPNMCUSTOMDRAW>(pNMHDR);
       *pResult = 0;
       if (isInMotion)
       {
              KillTimer(timerNum); 
              motionRate = motionRateSlider.GetPos();
              timerNum = SetTimer(1, motionRate, 0);
       }
}


RECT CMFCprojDlg::getBlockingRectangle (CPoint startP, CPoint endP)
{
       RECT r;
       r.left=(startP.x<endP.x)?(startP.x-6):(endP.x-6);         
       r.top=(startP.y<endP.y)?(startP.y-7):(endP.y-7);   
       r.right=(startP.x>endP.x)?(startP.x+6):(endP.x+6);        
       r.bottom=(startP.y>endP.y)?(startP.y+7):(endP.y+7);  

       return r;
}


// Needed for the double buffering - otherwise flickering
BOOL CMFCprojDlg::OnEraseBkgnd(CDC* pDC)
{
     return TRUE;
}

 

void CMFCprojDlg::setBitMap ()

{
       CRect r;
       RGBQUAD m_rgbPalette[256];
       BYTE* m_pBitmapBits;

       GetClientRect (r);
       LPBITMAPINFO lpbi;

       lpbi = (LPBITMAPINFO) new BYTE[sizeof(BITMAPINFOHEADER) + (256 * sizeof(RGBQUAD))];
       lpbi->bmiHeader.biSize = sizeof(BITMAPINFOHEADER);
       lpbi->bmiHeader.biWidth = r.Width ();
       lpbi->bmiHeader.biHeight = r.Height ();
       lpbi->bmiHeader.biPlanes = 1;
       lpbi->bmiHeader.biBitCount = 24;
       lpbi->bmiHeader.biCompression = BI_RGB;
       lpbi->bmiHeader.biSizeImage = WIDTHBYTES((DWORD)r.Width() * 8) * r.Height ();
       lpbi->bmiHeader.biXPelsPerMeter = 0;
       lpbi->bmiHeader.biYPelsPerMeter = 0;
       lpbi->bmiHeader.biClrUsed = 0;
       lpbi->bmiHeader.biClrImportant = 0;
       memcpy( lpbi->bmiColors, m_rgbPalette, sizeof(RGBQUAD) * 256 );

       HBITMAP hBitmap = CreateDIBSection(
              NULL,                    
              lpbi,                    
              DIB_RGB_COLORS,          
              (void **)&m_pBitmapBits, 
              NULL,
              0 );

       delete[] lpbi;
       if (screenBmp !=NULL)
              screenBmp = new CBitmap ();

       screenBmp->Attach(hBitmap);
}

 

void CMFCprojDlg::OnKillfocusEditText()
{

       // TODO: Add your control notification handler code here

}

//Prevent dialog from closing itself when enter is pressed
void CMFCprojDlg::OnOK ()
{
       if (GetDlgItem(IDOK) == GetFocus())
       {
              CDialogEx::OnOK();
              return;
       }
}
 
void CMFCprojDlg::OnCancel ()
{
       if (GetDlgItem(IDCANCEL) == GetFocus())
       {
              CDialogEx::OnOK();
              return;
       }
}