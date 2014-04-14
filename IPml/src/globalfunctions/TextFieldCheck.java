package globalfunctions;

public class TextFieldCheck 
{
	
	public static boolean isInt(String s)
	{
		boolean b=true;
        for(int i = 0; i < s.length(); i++)
        {
            if(!Character.isDigit(s.charAt(i)))
            {
            	b = false;
            }
        }
        if(b==true)
        {
        	int num = Integer.parseInt(s);
        	if(num>=0 && num<=255)
        		b = true;
        	else
        		b = false;
        }
        return b;
	}
}
