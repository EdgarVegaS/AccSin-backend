package com.accsin.services;

import static com.accsin.utils.DateTimeUtils.getMonthYear;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import com.accsin.entities.views_entities.ScheduleServiceRequestView;
import com.accsin.repositories.ScheduleNextMonthRepository;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import org.springframework.stereotype.Service;

@Service
public class ReportabilityService { 

    private ScheduleNextMonthRepository scheduleNextMonthRepository;

    public ReportabilityService(ScheduleNextMonthRepository scheduleNextMonthRepository){
        this.scheduleNextMonthRepository = scheduleNextMonthRepository;
    }
    
    public void createServicesPDF(String userId,String usrName,String startDate,String endDate) throws DocumentException, MalformedURLException, IOException{

        List<ScheduleServiceRequestView> ScheduleUser = scheduleNextMonthRepository.getBetweenDateByUser(userId, startDate, endDate);
        if (!ScheduleUser.isEmpty()) {
            String userName = ScheduleUser.get(0).getUserName();
            String userRut = ScheduleUser.get(0).getUserRut();

            Paragraph emptyLine = new Paragraph(" ");

            BaseColor colorHeader = WebColors.getRGBColor("#66ff99");
            BaseColor colorBase = WebColors.getRGBColor("#ccffdd");

            Document document = new Document();
            PdfWriter.getInstance(document,new FileOutputStream("C:/test/"+usrName+"-"+getMonthYear(startDate)+".pdf"));
            document.open();

            
            //Image img = Image.getInstance("resource/static/img/imgAccsin.jpg");
            
        
            Font fontTitle = new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.BOLD);
            Font fontText = new Font(Font.FontFamily.TIMES_ROMAN, 12);
            Paragraph title = new Paragraph("Reporte Mensual Estado de Servicios",fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);

            Paragraph name = new Paragraph("Nombre Cliente: "+userName+" |      Rut Cliente: "+userRut,fontText);
            name.setAlignment(Element.ALIGN_LEFT);

            PdfPTable table = new PdfPTable(5);
            PdfPCell c1 = new PdfPCell(new Phrase("Nombre del Servicio",fontText));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(colorHeader);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("Fecha del Servicio",fontText));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(colorHeader);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("Profesional Asignado",fontText));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(colorHeader);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("Estado del Servicio",fontText));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(colorHeader);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("Observaciones",fontText));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(colorHeader);
            table.addCell(c1);

            for (ScheduleServiceRequestView scheduleServiceRequestView : ScheduleUser) {
                PdfPCell cell = new PdfPCell(new Phrase(scheduleServiceRequestView.getNameService(),fontText));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(colorBase);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(scheduleServiceRequestView.getServiceDate().toString(),fontText));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(colorBase);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(scheduleServiceRequestView.getProfesionalName(),fontText));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(colorBase);
                table.addCell(cell);
                
                cell = new PdfPCell(new Phrase(scheduleServiceRequestView.isCompleted() ? "Completado" : "Incompleto",fontText));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(colorBase);
                table.addCell(cell);
                
                cell = new PdfPCell(new Phrase(scheduleServiceRequestView.getObservations(),fontText));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(colorBase);
                table.addCell(cell);

            }
            
            document.add(title);
            document.add(emptyLine);
            document.add(name);
            document.add(emptyLine);
            document.add(emptyLine);
            document.add(emptyLine);
            document.add(emptyLine);
            document.add(table);
            
            document.close();

        }
    }

}
