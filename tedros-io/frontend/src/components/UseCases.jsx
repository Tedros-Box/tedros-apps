import Button from './ui/Button';

const UseCases = () => {
    return (
        <section id="casos-de-uso" className="section-padding bg-[#0f172a]">
            <div className="container">
                <h2 className="text-3xl md:text-4xl font-bold mb-12 text-center">Impacto <span className="text-gradient">Real</span></h2>

                <div className="glass-card overflow-hidden">
                    <div className="grid grid-cols-1 lg:grid-cols-2">
                        <div className="p-8 lg:p-12 flex flex-col justify-center">
                            <div className="text-blue-500 font-bold mb-2">ONG Somos Social Movement</div>
                            <h3 className="text-2xl font-bold text-white mb-4">Transformação Digital no Terceiro Setor</h3>
                            <p className="text-slate-400 mb-6">
                                A ONG enfrentava desafios na gestão de voluntários, doações e divulgação. Com o Tedros Box, implementaram um back-office completo para controlar ações, estoque e comunicação.
                            </p>

                            <div className="grid grid-cols-2 gap-6 mb-8">
                                <div>
                                    <div className="text-2xl font-bold text-white mb-1">100%</div>
                                    <div className="text-xs text-slate-500">Gestão Centralizada</div>
                                </div>
                                <div>
                                    <div className="text-2xl font-bold text-white mb-1">Integrado</div>
                                    <div className="text-xs text-slate-500">Site + Backoffice</div>
                                </div>
                            </div>

                            <div className="flex gap-4">
                                <a href="http://www.somossocial.org.br/" target="_blank" rel="noopener noreferrer">
                                    <Button variant="outline">Visitar Site</Button>
                                </a>
                            </div>
                        </div>

                        <div className="relative bg-gradient-to-br from-blue-900 to-slate-900 min-h-[300px] flex items-center justify-center p-8">
                            {/* Abstract representation or placeholder for an image if we had one */}
                            <div className="text-center">
                                <blockquote className="italic text-slate-300 mb-4">
                                    "O Tedros permitiu que focássemos no que importa: ajudar pessoas. A parte técnica foi resolvida de forma transparente."
                                </blockquote>
                                <div className="text-sm font-bold text-blue-400">Caso de Sucesso</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    );
};

export default UseCases;
