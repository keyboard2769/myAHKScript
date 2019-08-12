
public final class ${name} {

  private static ${name} self = null;
  public static final ${name} ccGetInstance() {
    if(self==null){self=new ${name}();return self;}
    else{return self;}
  }//+++
  private ${name}(){}//++!

  //===

 }//***eof
 
