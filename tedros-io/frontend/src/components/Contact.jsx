import { useState } from 'react';
import Button from './ui/Button';
import { useTranslation, Trans } from 'react-i18next';

const Contact = () => {
    const { t } = useTranslation();
    const [formData, setFormData] = useState({ name: '', email: '', message: '' });
    const [status, setStatus] = useState(null); // 'sending', 'success', 'error'

    const handleSubmit = async (e) => {
        e.preventDefault();
        setStatus('sending');
        // Chamada correta ao endpoint backend (ContactResource) enviando JSON via POST
        try {
            const res = await fetch('api/contact', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(formData)
            });

            if (!res.ok) {
                throw new Error('Network response was not ok');
            }

            // Opcional: ler a resposta JSON do backend
            // const data = await res.json();
            setStatus('success');
            setFormData({ name: '', email: '', message: '' });
        } catch (error) {
            setStatus('error');
        }
    };

    return (
        <section id="contato" className="section-padding relative">
            <div className="container max-w-4xl">
                <div className="glass-card p-8 md:p-12">
                    <div className="text-center mb-10">
                        <h2 className="text-3xl font-bold mb-4">
                            <Trans i18nKey="contact.title">
                                Vamos <span className="text-gradient">Conversar?</span>
                            </Trans>
                        </h2>
                        <p className="text-slate-400">
                            {t('contact.subtitle')}
                        </p>
                    </div>

                    <form onSubmit={handleSubmit} className="space-y-6">
                        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                            <div>
                                <label className="block text-sm font-medium text-slate-300 mb-2">{t('contact.form.name')}</label>
                                <input
                                    type="text"
                                    required
                                    className="w-full bg-slate-900/50 border border-slate-700 rounded-lg px-4 py-3 text-white focus:border-blue-500 focus:outline-none transition-colors"
                                    placeholder={t('contact.form.namePlaceholder')}
                                    value={formData.name}
                                    onChange={(e) => setFormData({ ...formData, name: e.target.value })}
                                />
                            </div>
                            <div>
                                <label className="block text-sm font-medium text-slate-300 mb-2">{t('contact.form.email')}</label>
                                <input
                                    type="email"
                                    required
                                    className="w-full bg-slate-900/50 border border-slate-700 rounded-lg px-4 py-3 text-white focus:border-blue-500 focus:outline-none transition-colors"
                                    placeholder={t('contact.form.emailPlaceholder')}
                                    value={formData.email}
                                    onChange={(e) => setFormData({ ...formData, email: e.target.value })}
                                />
                            </div>
                        </div>

                        <div>
                            <label className="block text-sm font-medium text-slate-300 mb-2">{t('contact.form.message')}</label>
                            <textarea
                                required
                                rows={4}
                                className="w-full bg-slate-900/50 border border-slate-700 rounded-lg px-4 py-3 text-white focus:border-blue-500 focus:outline-none transition-colors"
                                placeholder={t('contact.form.messagePlaceholder')}
                                value={formData.message}
                                onChange={(e) => setFormData({ ...formData, message: e.target.value })}
                            ></textarea>
                        </div>

                        <div className="text-center">
                            <Button type="submit" variant="primary" className="w-full md:w-auto min-w-[200px]" disabled={status === 'sending'}>
                                {status === 'sending' ? t('contact.form.sending') : t('contact.form.submit')}
                            </Button>
                            {status === 'success' && <p className="mt-4 text-emerald-500">{t('contact.form.success')}</p>}
                            {status === 'error' && <p className="mt-4 text-red-500">{t('contact.form.error')}</p>}
                        </div>
                    </form>
                </div>
            </div>
        </section>
    );
};

export default Contact;