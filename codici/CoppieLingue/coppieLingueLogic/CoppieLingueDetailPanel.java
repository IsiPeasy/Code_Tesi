package com.space.carssbo.component.coppieLingue;

import com.space.CommonSession;
import com.space.Context;
import com.space.carssbo.Config;
import com.space.carssbo.component.ConfigOfa.ConfigOFAInserisciModificaPanel;
import com.space.carssbo.component.TestAreeStudio.AreeStudioElencoPanel;
import com.space.component.generic.DefaultPanel;
import com.space.component.generic.EnabledLabelBlockingAjaxButton;
import com.space.component.generic.UpperTextField;
import com.space.model.CorsoDiStudio;
import com.space.model.OperationType;
import com.space.model.coppieLingue.CoppiaLingue;
import com.space.model.coppieLingue.Lingua;
import com.space.model.master2rata.Master2Rata;
import com.space.model.master2rata.TipoMaster;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.util.ListModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class CoppieLingueDetailPanel extends DefaultPanel<CoppiaLingue> {

    private final Form form;
    private final Panel previous;
    private final IModel<CoppiaLingue> searchModel;
    private TextField<Integer> annoField;
    private TextField<String> cdcField;
    //private TextField<String> codLingua1;

    private DropDownChoice<Lingua> linguaDropDownChoice;
    private DropDownChoice<Lingua> lingua2DropDownChoice;
    private Lingua lingua1;
    private Lingua lingua2;


    private EnabledLabelBlockingAjaxButton inserisci;
    private EnabledLabelBlockingAjaxButton indietro;
    private ListModel<CoppiaLingue> coppiaLingueListModelModel;

    public CoppieLingueDetailPanel(String id, IModel<CoppiaLingue> model, Form form, Panel previous, IModel<CoppiaLingue> searchModel) {
        super(id, model);
        this.form = form;
        this.previous = previous;
        this.searchModel = searchModel;
        coppiaLingueListModelModel = new ListModel<>(new ArrayList<>());
        if (model.getObject().getAnnoacc() == null) { //da null qui
            model.getObject().setAnnoacc(Context.get().getCoppieLingueDao().loadAnnoAcc());
        }
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(annoField = new TextField<Integer>("annoField",
                new PropertyModel<>(getModel(), "annoacc"), Integer.class));
        annoField.setRequired(true);

        add(cdcField = new UpperTextField("cdcField",
                new PropertyModel<>(getModel(), "cscCod"), String.class));
        annoField.setRequired(false);

        linguaDropDownChoice = new DropDownChoice<Lingua>(
                "codLingua1",
                new PropertyModel<>(this, "lingua1"),
                Context.get().getCoppieLingueDao().listLingue(),
                new ChoiceRenderer<>("descrizione", "codice"));
        linguaDropDownChoice.setRequired(true);
        add(linguaDropDownChoice);

        lingua2DropDownChoice = new DropDownChoice<Lingua>(
                "codLingua2",
                new PropertyModel<>(this, "lingua2"),
                Context.get().getCoppieLingueDao().listLingue(),
                new ChoiceRenderer<>("descrizione", "codice"));
        lingua2DropDownChoice.setRequired(true);
        add(lingua2DropDownChoice);

        annoField.setRequired(false);

        cdcField.setOutputMarkupId(true);

        Buttonbar buttonbar = new Buttonbar("buttonbar");
        buttonbar.setOutputMarkupId(true);
        form.addOrReplace(buttonbar);
        inserisci = new EnabledLabelBlockingAjaxButton("inserisci", form, "mybody", Model.of("inserisci")) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

                try {

                    //Recepisco il codice delle lingua

                    getModelObject().setCodLingua1(lingua1.getCodice());
                    getModelObject().setCodLingua2(lingua2.getCodice());

                    Context.get().getCoppieLingueDao().save(getModelObject());

                    Context.get().getCoppieLingueDao().insertOperationLog(
                            Config.APPLICATION_ID.value().toString(),
                            OperationType.INSERT,
                            CoppieLingueDetailPanel.this.getModelObject(),
                            CoppieLingueDetailPanel.this.getClass(),
                            getSession().getId(),
                            "",
                            ((CommonSession) getSession()).getUser().getUsername());

                    Panel next = new CoppieLinguePanel("view", searchModel, form) {
                        @Override
                        protected void onExport(AjaxRequestTarget target) {

                        }

                        @Override
                        protected void openFeedbackDialog(AjaxRequestTarget target) {
                            CoppieLingueDetailPanel.this.openFeedbackDialog(target);
                        }
                    };
                    next.info("Inserimento effettuato con successo");
                    form.addOrReplace(next);
                    target.add(form);


                } catch (PersistenceException e) {
                    if (e.getCause() != null && e.getCause() instanceof SQLException) {
                        if (((SQLException) e.getCause()).getErrorCode() == 20001) {
                            CoppieLingueDetailPanel.this.getForm().error("IMPOSSIBILE INSERIRE IL DATO. DATO GIA PRESENTE. ");
                            openFeedbackDialog(target);
                        } else if (((SQLException) e.getCause()).getErrorCode() == 20002) {
                            CoppieLingueDetailPanel.this.getForm().error("IMPOSSIBILE INSERIRE IL DATO. SI PREGA DI VERIFICARE.");
                            openFeedbackDialog(target);
                        } else if (((SQLException) e.getCause()).getErrorCode() == 20003) {
                            CoppieLingueDetailPanel.this.getForm().error("IMPOSSIBILE INSERIRE IL DATO. DATO GIA PRESENTE CON LINGUE INVERTITE.");
                            openFeedbackDialog(target);
                        } else {
                            CoppieLingueDetailPanel.this.getForm().error("Impossibile salvare, verificare i dati inseriti");
                            openFeedbackDialog(target);
                        }
                    }
                }


            }
        };
        indietro = new EnabledLabelBlockingAjaxButton("indietro", form, "mybody", Model.of("indietro")) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                form.addOrReplace(new CoppieLinguePanel("view",
                        searchModel,
                        form) {
                    @Override
                    protected void onExport(AjaxRequestTarget target) {

                    }

                    @Override
                    protected void openFeedbackDialog(AjaxRequestTarget target) {
                        CoppieLingueDetailPanel.this.openFeedbackDialog(target);
                    }
                });
                target.add(form);
            }
        };
        indietro.setDefaultFormProcessing(false);
        buttonbar.add(inserisci);
        buttonbar.add(indietro);
    }

    protected abstract void openFeedbackDialog(AjaxRequestTarget target);

    protected abstract Form getForm();

    private class Buttonbar extends Panel {

        public Buttonbar(String id) {
            super(id);
        }
    }
}
