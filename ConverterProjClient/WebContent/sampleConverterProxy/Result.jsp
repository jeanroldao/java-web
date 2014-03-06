<%@page contentType="text/html;charset=UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<HTML>
<HEAD>
<TITLE>Result</TITLE>
</HEAD>
<BODY>
<H1>Result</H1>

<jsp:useBean id="sampleConverterProxyid" scope="session" class="wtp.ConverterProxy" />
<%
if (request.getParameter("endpoint") != null && request.getParameter("endpoint").length() > 0)
sampleConverterProxyid.setEndpoint(request.getParameter("endpoint"));
%>

<%
String method = request.getParameter("method");
int methodID = 0;
if (method == null) methodID = -1;

if(methodID != -1) methodID = Integer.parseInt(method);
boolean gotMethod = false;

try {
switch (methodID){ 
case 2:
        gotMethod = true;
        java.lang.String getEndpoint2mtemp = sampleConverterProxyid.getEndpoint();
if(getEndpoint2mtemp == null){
%>
<%=getEndpoint2mtemp %>
<%
}else{
        String tempResultreturnp3 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(getEndpoint2mtemp));
        %>
        <%= tempResultreturnp3 %>
        <%
}
break;
case 5:
        gotMethod = true;
        String endpoint_0id=  request.getParameter("endpoint8");
            java.lang.String endpoint_0idTemp = null;
        if(!endpoint_0id.equals("")){
         endpoint_0idTemp  = endpoint_0id;
        }
        sampleConverterProxyid.setEndpoint(endpoint_0idTemp);
break;
case 10:
        gotMethod = true;
        wtp.Converter getConverter10mtemp = sampleConverterProxyid.getConverter();
if(getConverter10mtemp == null){
%>
<%=getConverter10mtemp %>
<%
}else{
        if(getConverter10mtemp!= null){
        String tempreturnp11 = getConverter10mtemp.toString();
        %>
        <%=tempreturnp11%>
        <%
        }}
break;
case 13:
        gotMethod = true;
        String farenheit_1id=  request.getParameter("farenheit16");
        float farenheit_1idTemp  = Float.parseFloat(farenheit_1id);
        float farenheitToCelsius13mtemp = sampleConverterProxyid.farenheitToCelsius(farenheit_1idTemp);
        String tempResultreturnp14 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(farenheitToCelsius13mtemp));
        %>
        <%= tempResultreturnp14 %>
        <%
break;
case 18:
        gotMethod = true;
        String celsius_2id=  request.getParameter("celsius21");
        float celsius_2idTemp  = Float.parseFloat(celsius_2id);
        float celsiusToFarenheit18mtemp = sampleConverterProxyid.celsiusToFarenheit(celsius_2idTemp);
        String tempResultreturnp19 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(celsiusToFarenheit18mtemp));
        %>
        <%= tempResultreturnp19 %>
        <%
break;
}
} catch (Exception e) { 
%>
Exception: <%= org.eclipse.jst.ws.util.JspUtils.markup(e.toString()) %>
Message: <%= org.eclipse.jst.ws.util.JspUtils.markup(e.getMessage()) %>
<%
return;
}
if(!gotMethod){
%>
result: N/A
<%
}
%>
</BODY>
</HTML>