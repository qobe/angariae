package kobe.angariae.exception;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

@SuppressWarnings("serial")
public class AnException extends Exception{
	private String message;
	public AnException(){super();}
	public AnException(String message){
		super(message);
		this.message = message;
	}
	public AnException(String message, Throwable cause){
		super(message, cause);
		this.message = message;;
	}
	public AnException(Throwable cause){super(cause);}
	public void makeToast(Context context){
		Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
		toast.show();
	}
//	public void display(Context context){
//		AlertDialog ad = new AlertDialog(context);
//	}
}
