```markdown
You are an expert React developer working on the tedros-io frontend project, which is a Vite-based React app using Tailwind CSS, Framer Motion, and Lucide React. The current App.jsx structure is:

```jsx
import Layout from './components/Layout';
import Hero from './components/Hero';
import About from './components/About';
import Features from './components/Features';
import Screenshots from './components/Screenshots';
import UseCases from './components/UseCases';
import Contact from './components/Contact';

function App() {
    return (
        <Layout>
            <Hero />
            <About />
            <Features />
            <Screenshots />
            <UseCases />
            <Contact />
        </Layout>
    );
}

export default App;
```

The package.json is configured with dependencies like react, framer-motion, lucide-react, and dev deps like tailwindcss, vite, etc.

Your task is to add a new section to the website that demonstrates how simple it is to create a new CRUD screen for an entity in the Tedros Box system. Specifically, focus on creating a CRUD screen for "products". Insert this new section after the <UseCases /> component but before <Contact /> in App.jsx.

Create a new component file called `Tutorial.jsx` in the `./components` directory. This component should:

- Use a clean, modern design with Tailwind CSS classes, similar to other sections (e.g., full-width, with padding, dark/light mode support if applicable).
- Have a heading like "Como Criar uma Tela CRUD Simples no Tedros Box" (in Portuguese, meaning "How to Create a Simple CRUD Screen in Tedros Box").
- Explain briefly in text: "Veja como é simples adicionar uma nova tela CRUD para a entidade 'Product' no sistema Tedros Box. A seguir, os passos em ordem de precedência, com trechos de código das classes envolvidas."
- Display the steps in order of precedence as an ordered list or step-by-step sections, showing code snippets for each class. Use syntax-highlighted code blocks (e.g., with <pre> and <code> tags, or a simple Tailwind-styled box). The classes and their projects are:
  1. org.tedros.stock.entity.Product (Projeto: app-stock-model) – Show a placeholder or example Java code snippet for this entity class (e.g., a simple POJO with fields like id, name, price).
  2. org.tedros.stock.module.products.model.ProductMV (Projeto: app-stock-fx) – Show an example code snippet for this model-view class (e.g., extending a base MV class).
  3. org.tedros.stock.module.products.ProductModule (Projeto: app-stock-fx) – Show an example module registration code.
  4. org.tedros.stock.start.AppStart (Projeto: app-stock-fx) – Show an example app startup code that integrates the module.
- After the code steps, show an example Maven project structure for a Tedros Box application as a text-based tree diagram in a code block:
  ```
  /app-stock
  |-- app-stock-ejb
  |-- app-stock-ejb-client
  |-- app-stock-ejb-ear
  |-- app-stock-fx
  |-- app-stock-model
  ```
- Finally, display the image of the generated screen: Use an <img> tag sourcing from `/images/Screenshots/115216.png` (assuming it's in public/images/Screenshots). Make it responsive with Tailwind (e.g., max-w-full, rounded-lg, with a caption like "Tela de Produtos Gerada no Tedros Box").
- Add subtle animations using Framer Motion for section fade-in or code block reveals.
- Ensure the component is exported as default.

Update App.jsx to import Tutorial and add <Tutorial /> after <UseCases />.

Generate the complete code for the new Tutorial.jsx file and the updated App.jsx. Do not modify other files. Ensure everything is responsive and matches the site's style.
```