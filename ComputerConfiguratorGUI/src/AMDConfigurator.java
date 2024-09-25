class AMDConfigurator extends Configurator {
    public AMDConfigurator() {
        super(599.0);
    }

    @Override
    public void addProcessor(int option) {
        double processorPrice = switch (option) {
            case 2 -> {
                configurationSummary.append("Processor: FX-8350 (+$25)\n");
                yield 25.0;
            }
            case 3 -> {
                configurationSummary.append("Processor: FX-9590 (+$90)\n");
                yield 90.0;
            }
            case 4 -> {
                configurationSummary.append("Processor: FX-4100 (+$187)\n");
                yield 187.0;
            }
            case 5 -> {
                configurationSummary.append("Processor: FX-4300 (+$280)\n");
                yield 280.0;
            }
            default -> {
                configurationSummary.append("Processor: FX-2100 (Included with Base Package)\n");
                yield 0.0;
            }
        };
        runningTotal += processorPrice;
    }

    @Override
    public void addMemory(int option) {
        double memoryPrice;
        switch (option) {
            case 2 -> {
                configurationSummary.append("Memory: 6GB Dual Channel DDR3 1600MHz (+$28)\n");
                memoryPrice = 28.0;
            }
            case 3 -> {
                configurationSummary.append("Memory: 8GB Dual Channel DDR3 1600MHz (+$58)\n");
                memoryPrice = 58.0;
            }
            case 4 -> {
                configurationSummary.append("Memory: 12GB Dual Channel DDR3 1600MHz (+$108)\n");
                memoryPrice = 108.0;
            }
            case 5 -> {
                configurationSummary.append("Memory: 16GB Dual Channel DDR3 1600MHz (+$176)\n");
                memoryPrice = 176;
            }
            default -> {
                configurationSummary.append("Memory: 4GB Dual Channel DDR3 1600MHz (Included with Base Package)\n");
                memoryPrice = 0.0;
            }
        }
        runningTotal += memoryPrice;
    }

    @Override
    public void addGraphicsCard(int option) {
        double graphicPrice = switch (option) {
            case 2 -> {
                configurationSummary.append("Graphics Card: NVIDIA GeForce G310 512MB DDR3 (+$80)\n");
                yield 80.0;
            }
            case 3 -> {
                configurationSummary.append("Graphics Card: NVIDIA GeForce GT620 1GB DDR3 (+$169)\n");
                yield 169.0;
            }
            case 4 -> {
                configurationSummary.append("Graphics Card: NVIDIA GeForce GT640 1GB GDDR5 (+$490)\n");
                yield 490.0;
            }
            default -> {
                configurationSummary.append("Integrated 3D Graphics (Included with Base Package)\n");
                yield 0.0;
            }
        };
        runningTotal += graphicPrice;
    }
}