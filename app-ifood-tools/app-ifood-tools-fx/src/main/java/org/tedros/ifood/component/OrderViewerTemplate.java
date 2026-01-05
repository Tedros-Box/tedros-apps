package org.tedros.ifood.component;

/**
 * Provides the HTML template for the Order Viewer WebView.
 */
public class OrderViewerTemplate {

    public static String getHtml() {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <style>\n" +
                "        :root {\n" +
                "            --ifood-red: #EA1D2C;\n" +
                "            --bg-color: #ffffff;\n" +
                "            --text-color: #333333;\n" +
                "            --grey-light: #f4f4f4;\n" +
                "            --border-color: #dddddd;\n" +
                "        }\n" +
                "        body {\n" +
                "            font-family: -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, Helvetica, Arial, sans-serif;\n"
                +
                "            margin: 0; padding: 0;\n" +
                "            background-color: var(--bg-color);\n" +
                "            color: var(--text-color);\n" +
                "            font-size: 14px;\n" +
                "        }\n" +
                "        .tabs {\n" +
                "            display: flex;\n" +
                "            border-bottom: 2px solid var(--ifood-red);\n" +
                "            background-color: var(--grey-light);\n" +
                "            position: sticky; top: 0; z-index: 100;\n" +
                "        }\n" +
                "        .tab {\n" +
                "            padding: 12px 20px;\n" +
                "            cursor: pointer;\n" +
                "            font-weight: bold;\n" +
                "            transition: background 0.3s;\n" +
                "        }\n" +
                "        .tab.active {\n" +
                "            background-color: var(--ifood-red);\n" +
                "            color: white;\n" +
                "        }\n" +
                "        .tab:hover:not(.active) {\n" +
                "            background-color: #e0e0e0;\n" +
                "        }\n" +
                "        .content {\n" +
                "            padding: 20px;\n" +
                "            display: none;\n" +
                "        }\n" +
                "        .content.active {\n" +
                "            display: block;\n" +
                "        }\n" +
                "        h2, h3 { margin-top: 0; color: var(--ifood-red); }\n" +
                "        .section {\n" +
                "            margin-bottom: 20px;\n" +
                "            padding: 15px;\n" +
                "            border: 1px solid var(--border-color);\n" +
                "            border-radius: 8px;\n" +
                "        }\n" +
                "        .item-row {\n" +
                "            border-bottom: 1px solid var(--grey-light);\n" +
                "            padding: 10px 0;\n" +
                "        }\n" +
                "        .item-main {\n" +
                "            display: flex;\n" +
                "            align-items: center;\n" +
                "            justify-content: space-between;\n" +
                "        }\n" +
                "        .item-info {\n" +
                "            display: flex;\n" +
                "            align-items: center;\n" +
                "        }\n" +
                "        .item-img {\n" +
                "            width: 40px; height: 40px; border-radius: 4px;\n" +
                "            margin-right: 12px; object-fit: cover;\n" +
                "            background-color: var(--grey-light);\n" +
                "        }\n" +
                "        .nested { margin-left: 20px; font-size: 0.9em; color: #666; }\n" +
                "        .nested-item { margin-top: 4px; }\n" +
                "        .totals-table {\n" +
                "            width: 100%;\n" +
                "            border-top: 2px solid var(--border-color);\n" +
                "            margin-top: 10px;\n" +
                "            padding-top: 10px;\n" +
                "        }\n" +
                "        .total-row {\n" +
                "            display: flex;\n" +
                "            justify-content: space-between;\n" +
                "            margin-bottom: 6px;\n" +
                "        }\n" +
                "        .grand-total {\n" +
                "            font-size: 1.5em;\n" +
                "            font-weight: bold;\n" +
                "            color: var(--ifood-red);\n" +
                "            margin-top: 15px;\n" +
                "            border-top: 1px solid var(--border-color);\n" +
                "            padding-top: 10px;\n" +
                "        }\n" +
                "        pre { background: #282c34; color: #abb2bf; padding: 15px; border-radius: 8px; overflow: auto; }\n"
                +
                "    </style>\n" +
                "    <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.7.0/styles/github-dark.min.css\">\n"
                +
                "    <script src=\"https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.7.0/highlight.min.js\"></script>\n"
                +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"tabs\">\n" +
                "        <div class=\"tab active\" onclick=\"switchTab('summary')\">Order Summary</div>\n" +
                "        <div class=\"tab\" onclick=\"switchTab('raw')\">Raw Data</div>\n" +
                "    </div>\n" +
                "    \n" +
                "    <div id=\"summary\" class=\"content active\">\n" +
                "        <div id=\"summary-container\">Select an order to view details.</div>\n" +
                "    </div>\n" +
                "    \n" +
                "    <div id=\"raw\" class=\"content\">\n" +
                "        <pre id=\"json-view\"></pre>\n" +
                "    </div>\n" +
                "\n" +
                "    <script>\n" +
                "        function switchTab(tabId) {\n" +
                "            document.querySelectorAll('.tab').forEach(t => t.classList.remove('active'));\n" +
                "            document.querySelectorAll('.content').forEach(c => c.classList.remove('active'));\n" +
                "            \n" +
                "            document.querySelector(`.tab[onclick*=\"${tabId}\"]`).classList.add('active');\n" +
                "            document.getElementById(tabId).classList.add('active');\n" +
                "        }\n" +
                "\n" +
                "        function formatCurrency(v) {\n" +
                "            return new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(v || 0);\n"
                +
                "        }\n" +
                "\n" +
                "        function recursiveOptions(options) {\n" +
                "            if (!options || options.length === 0) return '';\n" +
                "            return `<div class=\"nested\">` + \n" +
                "                options.map(o => `\n" +
                "                    <div class=\"nested-item\">\n" +
                "                        + ${o.quantity}x ${o.name} (${formatCurrency(o.price)})\n" +
                "                        ${o.customization ? recursiveCustomization(o.customization) : ''}\n" +
                "                    </div>\n" +
                "                `).join('') + \n" +
                "                `</div>`;\n" +
                "        }\n" +
                "        \n" +
                "        function recursiveCustomization(custs) {\n" +
                "            if (!custs || custs.length === 0) return '';\n" +
                "            return `<div class=\"nested\">` + \n" +
                "                custs.map(c => `<div class=\"nested-item\">- ${c.quantity}x ${c.name}</div>`).join('') + \n"
                +
                "                `</div>`;\n" +
                "        }\n" +
                "\n" +
                "        function updateDetailView(data) {\n" +
                "            // Update JSON View\n" +
                "            const jsonCode = JSON.stringify(data, null, 2);\n" +
                "            document.getElementById('json-view').textContent = jsonCode;\n" +
                "            hljs.highlightElement(document.getElementById('json-view'));\n" +
                "\n" +
                "            // Update Summary View\n" +
                "            const container = document.getElementById('summary-container');\n" +
                "            \n" +
                "            let html = `\n" +
                "                <div class=\"section\">\n" +
                "                    <h3>Order Header</h3>\n" +
                "                    <p><b>Display ID:</b> ${data.displayId}</p>\n" +
                "                    <p><b>Type:</b> ${data.orderType}</p>\n" +
                "                    <p><b>Date:</b> ${new Date(data.createdAt).toLocaleString()}</p>\n" +
                "                </div>\n" +
                "                \n" +
                "                <div class=\"section\">\n" +
                "                    <h3>Customer & Delivery</h3>\n" +
                "                    <p><b>Name:</b> ${data.customer.name}</p>\n" +
                "                    <p><b>Address:</b> ${data.delivery?.deliveryAddress?.formattedAddress || 'N/A'}</p>\n"
                +
                "                </div>\n" +
                "\n" +
                "                <div class=\"section\">\n" +
                "                    <h3>Items</h3>\n" +
                "                    ${data.items.map(item => `\n" +
                "                        <div class=\"item-row\">\n" +
                "                            <div class=\"item-main\">\n" +
                "                                <div class=\"item-info\">\n" +
                "                                    <img class=\"item-img\" src=\"${item.imageUrl || ''}\" onerror=\"this.style.display='none'\">\n"
                +
                "                                    <span><b>${item.quantity}x ${item.name}</b></span>\n" +
                "                                </div>\n" +
                "                                <span>${formatCurrency(item.totalPrice)}</span>\n" +
                "                            </div>\n" +
                "                            ${item.options ? recursiveOptions(item.options) : ''}\n" +
                "                            ${item.observations ? `<div class=\"nested\" style=\"font-style: italic;\">Obs: ${item.observations}</div>` : ''}\n"
                +
                "                        </div>\n" +
                "                    `).join('')}\n" +
                "                </div>\n" +
                "\n" +
                "                <div class=\"section\">\n" +
                "                    <h3>Payments</h3>\n" +
                "                    ${data.payments.methods.map(m => `\n" +
                "                        <div class=\"total-row\">\n" +
                "                            <span>${m.method} (${m.type}) ${m.card ? `- ${m.card.brand}` : ''}</span>\n"
                +
                "                            <span>${formatCurrency(m.value)}</span>\n" +
                "                        </div>\n" +
                "                    `).join('')}\n" +
                "                </div>\n" +
                "\n" +
                "                <div class=\"totals-table\">\n" +
                "                    <div class=\"total-row\"><span>Subtotal</span><span>${formatCurrency(data.total.subTotal)}</span></div>\n"
                +
                "                    <div class=\"total-row\"><span>Delivery Fee</span><span>${formatCurrency(data.total.deliveryFee)}</span></div>\n"
                +
                "                    ${data.total.additionalFees ? `<div class=\"total-row\"><span>Additional Fees</span><span>${formatCurrency(data.total.additionalFees)}</span></div>` : ''}\n"
                +
                "                    <div class=\"grand-total\">\n" +
                "                        <span>TOTAL</span>\n" +
                "                        <span>${formatCurrency(data.total.orderAmount)}</span>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "            `;\n" +
                "            container.innerHTML = html;\n" +
                "        }\n" +
                "    </script>\n" +
                "</body>\n" +
                "</html>";
    }
}
