#pragma once
class CCommand
{
public:
       CCommand(void);
       ~CCommand(void);
       virtual void perform() = 0;
       virtual void undo() = 0;
};
 