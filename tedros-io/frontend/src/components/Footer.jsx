import { Github, Linkedin, Mail } from 'lucide-react';

const Footer = () => {
    return (
        <footer className="bg-[#0f172a] border-t border-gray-800 py-12">
            <div className="container">
                <div className="grid grid-cols-1 md:grid-cols-4 gap-8 mb-12">
                    <div className="col-span-1 md:col-span-2">
                        <h3 className="text-2xl font-bold font-[Outfit] text-white mb-4">
                            Tedros<span className="text-blue-500">.io</span>
                        </h3>
                        <p className="text-slate-400 max-w-sm mb-6">
                            Solução completa para gestão corporativa com inteligência artificial nativa. Reduza custos e escale seu negócio com eficiência.
                        </p>
                        <div className="flex gap-4">
                            <a href="https://github.com/Tedros-Box/tedros-apps/" target="_blank" rel="noopener noreferrer" className="text-slate-400 hover:text-white transition-colors">
                                <Github size={20} />
                            </a>
                            <a href="https://www.linkedin.com/company/tedros" target="_blank" rel="noopener noreferrer" className="text-slate-400 hover:text-blue-500 transition-colors">
                                <Linkedin size={20} />
                            </a>
                            <a href="mailto:contact@tedros.io" className="text-slate-400 hover:text-white transition-colors">
                                <Mail size={20} />
                            </a>
                        </div>
                    </div>

                    <div>
                        <h4 className="text-white font-semibold mb-4">Produto</h4>
                        <ul className="space-y-2">
                            <li><a href="#recursos" className="text-slate-400 hover:text-blue-500 text-sm">Recursos</a></li>
                            <li><a href="#arquitetura" className="text-slate-400 hover:text-blue-500 text-sm">Arquitetura</a></li>
                            <li><a href="#casos-de-uso" className="text-slate-400 hover:text-blue-500 text-sm">Casos de Uso</a></li>
                        </ul>
                    </div>

                    <div>
                        <h4 className="text-white font-semibold mb-4">Empresa</h4>
                        <ul className="space-y-2">
                            <li><a href="#sobre" className="text-slate-400 hover:text-blue-500 text-sm">Sobre Nós</a></li>
                            <li><a href="#parceria" className="text-slate-400 hover:text-blue-500 text-sm">Seja Parceiro</a></li>
                            <li><a href="#contato" className="text-slate-400 hover:text-blue-500 text-sm">Contato</a></li>
                        </ul>
                    </div>
                </div>

                <div className="border-t border-gray-800 pt-8 flex flex-col md:flex-row justify-between items-center text-sm text-slate-500">
                    <p>© {new Date().getFullYear()} Tedros Box. Todos os direitos reservados.</p>
                    <p>Dux XCode Engenharia</p>
                </div>
            </div>
        </footer>
    );
};

export default Footer;
