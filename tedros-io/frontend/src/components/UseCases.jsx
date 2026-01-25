import Button from './ui/Button';
import { useTranslation, Trans } from 'react-i18next';

const UseCases = () => {
    const { t } = useTranslation();

    const cases = [
        {
            company: "ONG Somos Social Movement",
            title: t('useCases.somos.title'),
            desc: t('useCases.somos.desc'),
            stats: [
                { value: "100%", label: "Centralizado" },
                { value: "Web+Desk", label: "Integração" }
            ],
            link: "http://www.somossocial.org.br/",
            hasLink: true
        }
    ];

    return (
        <section id="casos-de-uso" className="section-padding bg-[#0f172a]">
            <div className="container">
                <h2 className="text-3xl md:text-4xl font-bold mb-12 text-center">
                    <Trans i18nKey="useCases.title">
                        Real <span className="text-gradient">Impact</span>
                    </Trans>
                </h2>

                <div className="flex flex-col gap-12">
                    {cases.map((useCase, index) => (
                        <div key={index} className="glass-card overflow-hidden">
                            <div className="grid grid-cols-1 lg:grid-cols-2">
                                <div className="p-8 lg:p-12 flex flex-col justify-center">
                                    <div className="text-blue-500 font-bold mb-2">{useCase.company}</div>
                                    <h3 className="text-2xl font-bold text-white mb-4">{useCase.title}</h3>
                                    <p className="text-slate-400 mb-6">
                                        {useCase.desc}
                                    </p>

                                    <div className="grid grid-cols-2 gap-6 mb-8">
                                        {useCase.stats.map((stat, i) => (
                                            <div key={i}>
                                                <div className="text-2xl font-bold text-white mb-1">{stat.value}</div>
                                                <div className="text-xs text-slate-500">{stat.label}</div>
                                            </div>
                                        ))}
                                    </div>

                                    {useCase.hasLink && (
                                        <div className="flex gap-4">
                                            <a href={useCase.link} target="_blank" rel="noopener noreferrer">
                                                <Button variant="outline">Visit Website</Button>
                                            </a>
                                        </div>
                                    )}
                                </div>

                                <div className={`relative min-h-[300px] flex items-center justify-center p-8 ${index % 2 === 0 ? 'bg-gradient-to-br from-blue-900/50 to-slate-900' : 'bg-gradient-to-br from-violet-900/50 to-slate-900'}`}>
                                    <div className="text-center">
                                        <blockquote className="italic text-slate-300 mb-4 max-w-sm mx-auto">
                                            {t('useCases.somos.quote')}
                                        </blockquote>
                                        <div className="text-sm font-bold text-blue-400">{t('useCases.somos.success')}</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    ))}
                </div>
            </div>
        </section>
    );
};

export default UseCases;
