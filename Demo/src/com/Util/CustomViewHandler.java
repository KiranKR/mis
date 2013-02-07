package com.Util;  
import java.io.IOException;  
import java.util.Locale;  
  
import javax.faces.FacesException;  
import javax.faces.application.ViewHandler;  
import javax.faces.application.ViewHandlerWrapper;  
import javax.faces.component.UIViewRoot;  
import javax.faces.context.FacesContext;  
import javax.servlet.http.HttpServletRequest;  
   
public class CustomViewHandler extends ViewHandlerWrapper {  
   private ViewHandler parent;  
   public CustomViewHandler( ViewHandler  parent)   
   {  
       super();  
       for ( int k = 0 ; k < 20 ; k++ )  
            System.out.println(k + "aftersup" + parent);  
       this.parent = parent;  
       for ( int k = 0 ; k < 20 ; k++ )  
            System.out.println(k + "aftersup2" + this.parent);  
   }  
   public ViewHandler getWrapped (){return parent;}  
    /*public CustomViewHandler(ViewHandler parent) { 
        for ( int k = 0 ; k < 20 ; k++ ) 
        System.out.println(k + "CustomViewHandler.CustomViewHandler():Parent View Handler: "+parent.getClass()); 
        this.parent = parent; 
    }*/  
    /** @Override public UIViewRoot restoreView(FacesContext facesContext, String viewId) { 
     
     * {@link javax.faces.application.ViewExpiredException}. This happens only  when we try to logout from timed out pages. 
      
    UIViewRoot root =null;  
    for ( int k = 0 ; k < 20 ; k++ ) 
    System.out.println(k + "restorein" ); 
    root = super.restoreView(facesContext, viewId); 
    for ( int k = 0 ; k < 20 ; k++ ) 
        System.out.println(k + "restoreout" ); 
    if(root == null) {           
        root = createView(facesContext, viewId); 
    } 
    return root; 
   }*/  
   @Override public void initView(FacesContext context)  
           throws FacesException  
    {  
       for ( int k = 0 ; k < 4 ; k++ )  
            System.out.println(k + "initin" );  
            parent.initView( context );  
            for ( int k = 0 ; k < 4 ; k++ )  
                System.out.println(k + "initout" );  
    }  
    @Override public UIViewRoot restoreView(FacesContext facesContext, String viewId) {  
    /** 
     * {@link javax.faces.application.ViewExpiredException}. This happens only  when we try to logout from timed out pages. 
    */   
    UIViewRoot root =null;   
    for ( int k = 0 ; k < 4; k++ )  
    System.out.println(k + "restorein" );  
    root = parent.restoreView(facesContext, viewId);  
    for ( int k = 0 ; k < 4 ; k++ )  
        System.out.println(k + "restoreout" );  
    if(root == null) {            
        root = createView(facesContext, viewId);  
    }  
    return root;  
 }  
   
 @Override  
    public Locale calculateLocale(FacesContext facesContext) {  
        return parent.calculateLocale(facesContext);  
    }  
   
    @Override  
    public String calculateRenderKitId(FacesContext facesContext) {  
        String renderKitId = parent.calculateRenderKitId(facesContext);  
        //System.out.println("CustomViewHandler.calculateRenderKitId():RenderKitId: "+renderKitId);  
        return renderKitId;  
    }  
   
    @Override  
    public UIViewRoot createView(FacesContext facesContext, String viewId) {  
        for ( int k = 0 ; k < 4 ; k++ )  
            System.out.println(k + " create" );  
        return parent.createView(facesContext, viewId);  
    }  
   
   
    @Override  
    public String getActionURL(FacesContext facesContext, String actionId) {  
        return parent.getActionURL(facesContext, actionId);  
    }  
   
    @Override  
    public String getResourceURL(FacesContext facesContext, String resId) {  
        return parent.getResourceURL(facesContext, resId);  
    }  
   
    @Override  
    public void renderView(FacesContext facesContext, UIViewRoot viewId) throws IOException, FacesException {  
        parent.renderView(facesContext, viewId);  
   
    }  
   
    @Override  
    public void writeState(FacesContext facesContext) throws IOException {  
        parent.writeState(facesContext);  
    }  
   
    public ViewHandler getParent() {  
        return parent;  
    }  
   
}