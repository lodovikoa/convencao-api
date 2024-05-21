package com.lodoviko.convencao.infra.service.email;

import com.lodoviko.convencao.core.email.EmailProperties;
import com.lodoviko.convencao.domain.exception.EmailException;
import com.lodoviko.convencao.domain.service.EnvioEmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

@Service
public class SmtpEnvioEmailService implements EnvioEmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailProperties emailProperties;

    @Autowired
    private Configuration fremakerConfig;
    @Override
    public void enivar(Mensagem mensagem) {
        try {
            String corpo = this.processarTemplate(mensagem);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            helper.setFrom(emailProperties.getRemetente());
            helper.setTo(mensagem.getDestinatarios().toArray(new String[0]));
            helper.setSubject(mensagem.getAssunto());
            helper.setText(corpo, true);

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new EmailException("Não foi possível enviar e-mail", e);
        }
    }

    private String processarTemplate(Mensagem mensagem) {
        try {
            Template template = fremakerConfig.getTemplate(mensagem.getCorpo());

            return FreeMarkerTemplateUtils.processTemplateIntoString(template, mensagem.getVariaveis());
        } catch (Exception e) {
            throw new EmailException("Não foi possível montar o template do e-mail,", e);
        }
    }
}
